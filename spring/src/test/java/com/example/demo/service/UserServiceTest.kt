package com.example.demo.service

import com.example.demo.config.annotation.LocalBootTest
import com.example.demo.generator.MockUserBuilder
import com.example.demo.security.CustomBCryptPasswordEncoder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@LocalBootTest
@Transactional
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var customBCryptPasswordEncoder: CustomBCryptPasswordEncoder

    @Autowired
    lateinit var mockUserBuilder: MockUserBuilder

    private val rawPassword = "password"

    @Test
    @DisplayName("회원 가입/조회 테스트")
    @Throws(Exception::class)
    fun saveUserTest() {
        // given
        val user = mockUserBuilder.build("demo", "email@demo.com", rawPassword)

        // when
        val result = userService.findById(user.id ?: fail("mock 유저 저장 실패"))

        // then
        assertNotNull(result)
        assertEquals(user.loginId, result?.loginId)
        assertEquals(user.email, result?.email)
    }

    @Test
    @DisplayName("패스워드 암호화 테스트")
    @Throws(Exception::class)
    fun userPasswordEncryptionTest() {
        // given
        val user = mockUserBuilder.build("demo", "email@demo.com", rawPassword)
        val encodePassword = mockUserBuilder.uiEncode(rawPassword)

        // when
        val result = userService.findById(user.id ?: fail("mock 유저 저장 실패"))

        // then
        assertNotNull(result)
        assertNotEquals(rawPassword, result?.password)
        assertTrue(customBCryptPasswordEncoder.matches(encodePassword!!, result?.password!!))
    }
}