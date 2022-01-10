package com.example.demo.security.jwt

import com.example.demo.service.UserService
import com.example.demo.web.dto.Login
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import mu.KLogging
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider (
    private val passwordDecoder: PasswordDecoder,
    private val userService: UserService,
    @Value("\${jwt.secret-key}") private val secretKey: String
) {
    val key: Key = Keys.hmacShaKeyFor(Base64.decodeBase64(secretKey))

    fun generateToken(login: Login): String {
        val user = userService.findByLoginId(login.loginId)

        return user?.password?.let {
            if (passwordDecoder.matches(login.encodedPassword, it)) {
                val now = Date().time

                Jwts.builder()
                    .setSubject(login.loginId)
                    .setIssuedAt(Date())
                    .setExpiration(Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact()
            } else {
                throw IllegalArgumentException("Password is wrong.")
            }
        } ?: throw IllegalArgumentException("User does not exist.")
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

    companion object: KLogging() {
        private const val ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 // 1 분
        private const val REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 5 // 5 분
    }
}