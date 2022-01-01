package com.example.demo.security

import lombok.extern.slf4j.Slf4j
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
@Slf4j
class CustomBCryptPasswordEncoder(private val authenticationSecurityService: AuthenticationSecurityService) : BCryptPasswordEncoder() {
    override fun encode(rawPassword: CharSequence): String {
        return super.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return try {
            super.matches(
                    authenticationSecurityService.decrypt(rawPassword.toString()),
                    encodedPassword
            )
        } catch (e: Exception) {
            CustomBCryptPasswordEncoder.log.error("password decode fail", e)
            e.printStackTrace()
            false
        }
    }
}