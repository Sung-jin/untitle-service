package com.example.demo.security.jwt

import com.example.demo.security.UserPrincipal
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import mu.KLogging
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.InvalidParameterException
import java.security.Key
import java.util.*

@Component
class TokenProvider (
    @Value("\${jwt.secret-key}") secretKey: String
) {
    val key: Key = Keys.hmacShaKeyFor(Base64.decodeBase64(secretKey))

    fun generateToken(authentication: Authentication) {
        val now = Date().time
        val authorities: String = authentication
            .authorities
            .map(GrantedAuthority::getAuthority)
            .joinToString { "," }
        val accessToken: String = Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(Date(now + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
        val refreshToken: String = Jwts.builder()
            .setExpiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        (authentication.principal as UserPrincipal).apply {
            this.user?.accessToken = accessToken
            this.user?.refreshToken = refreshToken
            this.user?.expiredTimeIn = now + ACCESS_TOKEN_EXPIRE_TIME
        }
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims: Claims = try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
        } catch (e: ExpiredJwtException) {
            logger.error("Token is expired")
            e.claims
        }

        if (claims[AUTHORITIES_KEY] == null) throw RuntimeException("Unauthorized token")

        val authorities = claims[AUTHORITIES_KEY].toString().split(",")
            .map{ role -> SimpleGrantedAuthority(role) }

//        val principal: UserDetails = new User

//        return UsernamePasswordAuthenticationToken(User(), "", authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)

            return true
        } catch (e: SecurityException) {
            logger.error("Invalid JWT signature", e)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT signature", e)
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT signature", e)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT signature", e)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT token is invalid", e)
        }

        return false
    }

    fun validTokenAndReturnBody(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch(e: Exception) {
            e.printStackTrace();
            throw InvalidParameterException("Invalid token");
        }
    }

    fun getName(token: String): String {
        return validTokenAndReturnBody(token).get("loginId", String::class.java)
    }

    fun isTokenExpired(token: String): Boolean {
        return validTokenAndReturnBody(token).expiration.before(Date())
    }

    fun doGenerateToken(loginId: String, expiredTime: Long): String {
        val claims: Claims = Jwts.claims()
        claims["loginId"] = loginId

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiredTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateAccessToken(loginId: String): String {
        return doGenerateToken(loginId, ACCESS_TOKEN_EXPIRE_TIME)
    }

    fun generateRefreshToken(loginId: String): String {
        return doGenerateToken(loginId, REFRESH_TOKEN_EXPIRE_TIME)
    }

    companion object: KLogging() {
        private const val AUTHORITIES_KEY = "auth"
//        private const val BEARER_TYPE = "bearer"
        private const val ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 // 1 분
        private const val REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 5 // 5 분
    }
}