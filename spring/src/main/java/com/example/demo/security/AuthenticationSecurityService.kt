package com.example.demo.security

import lombok.extern.slf4j.Slf4j
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.codec.Hex
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpSession

@Service
@Slf4j
class AuthenticationSecurityService(private val httpSession: HttpSession) {
    @Value("\${security.pbkdf2.iteration-count}")
    private val securityIterationCount = 0

    @Value("\${security.pbkdf2.key-size}")
    private val securityKeySize = 0

    @Value("\${security.pbkdf2.passphrase}")
    private val securityPassphrase: String? = null

    @Value("\${security.pbkdf2.iv}")
    private val securityIv: String? = null
    fun saltKey(): String {
        return String(
                Hex.encode(httpSession.id
                        .replace("-", "")
                        .substring(0, 16)
                        .toByteArray(StandardCharsets.UTF_8)
                )
        )
    }

    @Throws(Exception::class)
    fun decrypt(encryptionText: String?): String {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec = PBEKeySpec(securityPassphrase!!.toCharArray(), Hex.decode(saltKey()), securityIterationCount, securityKeySize)
        val key = SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(Hex.decode(securityIv)))
        return String(
                cipher.doFinal(Base64.decodeBase64(encryptionText)),
                StandardCharsets.UTF_8
        )
    }
}