package com.example.demo.security.jwt

import com.example.demo.security.UserPrincipal
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider (
    @Value("\${jwt.secret-key}") private val secretKey: String
) {
    val key: Key = Keys.hmacShaKeyFor(Base64.decodeBase64(secretKey))

    fun generateToken(authentication: Authentication): String {
        val now = Date().time

        return Jwts.builder()
            .setSubject((authentication.principal as UserPrincipal).username)
            .setIssuedAt(Date())
            .setExpiration(Date(now + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUserIdFromJWT(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)

            return true
        } catch (e: SecurityException) {
            TokenProvider.logger.error("Invalid JWT signature", e)
        } catch (e: MalformedJwtException) {
            TokenProvider.logger.error("Invalid JWT signature", e)
        } catch (e: ExpiredJwtException) {
            TokenProvider.logger.error("Expired JWT signature", e)
        } catch (e: UnsupportedJwtException) {
            TokenProvider.logger.error("Unsupported JWT signature", e)
        } catch (e: IllegalArgumentException) {
            TokenProvider.logger.error("JWT token is invalid", e)
        }

        return false
    }

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 // 1 분
        private const val REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 5 // 5 분
    }
}