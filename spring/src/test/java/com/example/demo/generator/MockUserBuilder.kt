package com.example.demo.generator

import com.example.demo.entity.user.User
import com.example.demo.security.AuthenticationSecurityService
import com.example.demo.service.UserService
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.crypto.codec.Hex
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

@Component
class MockUserBuilder {
    @Value("\${security.pbkdf2.iteration-count}")
    private val securityIterationCount = 0

    @Value("\${security.pbkdf2.key-size}")
    private val securityKeySize = 0

    @Value("\${security.pbkdf2.passphrase}")
    private val securityPassphrase: String? = null

    @Value("\${security.pbkdf2.iv}")
    private val securityIv: String? = null

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val authenticationSecurityService: AuthenticationSecurityService? = null
    private val session = Mockito.mock(MockHttpSession::class.java)
    private val request = MockHttpServletRequest()
    @Throws(Exception::class)
    fun build(loginId: String?, email: String?, password: String?): User {
        var loginId = loginId
        var email = email
        var password = password
        if (loginId == null) loginId = "demo"
        if (email == null) email = "demo@demo.com"
        if (password == null) password = "password"
        return userService!!.save(
                User.builder()
                        .loginId(loginId)
                        .email(email)
                        .savePassword(uiEncode(password))
                        .build()
        )
    }

    fun uiEncode(rawPassword: String): String? {
        return try {
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec = PBEKeySpec(
                    securityPassphrase!!.toCharArray(),
                    Hex.decode(authenticationSecurityService!!.saltKey()),
                    securityIterationCount,
                    securityKeySize
            )
            val key = SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(Hex.decode(securityIv)))
            String(Base64.getEncoder().encode(cipher.doFinal(rawPassword.toByteArray(StandardCharsets.UTF_8))))
        } catch (e: Exception) {
            null
        }
    }

    init {
        MockitoAnnotations.openMocks(this)
        request.session = session
        request.requestURI = "/"
        request.remoteAddr = "10.10.10.10"
        request.addHeader("Authorization", "xAuth")
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(request))
        Mockito.`when`(session.id).thenReturn("c3da74ad-16a7-42c9-998d-b0cbef89d748")
    }
}