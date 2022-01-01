package com.example.demo.service

import com.example.demo.config.annotation.LocalBootTest
import com.example.demo.generator.MockUserBuilder
import com.example.demo.security.CustomBCryptPasswordEncoder
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@LocalBootTest
@Transactional
internal class UserServiceTest {
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val customBCryptPasswordEncoder: CustomBCryptPasswordEncoder? = null

    @Autowired
    private val mockUserBuilder: MockUserBuilder? = null
    private val rawPassword = "password"
    @Test
    @DisplayName("회원 가입/조회 테스트")
    @Throws(Exception::class)
    fun saveUserTest() {
        // given
        val user = mockUserBuilder!!.build("demo", "email@demo.com", rawPassword)

        // when
        val result = userService!!.findById(user.getId())

        // then
        Assertions.assertNotNull(result)
        assertEquals(user.getLoginId(), result.getLoginId())
        assertEquals(user.getEmail(), result.getEmail())
    }

    @Test
    @DisplayName("패스워드 암호화 테스트")
    @Throws(Exception::class)
    fun userPasswordEncryptionTest() {
        // given
        val user = mockUserBuilder!!.build("demo", "email@demo.com", rawPassword)
        val encodePassword = mockUserBuilder.uiEncode(rawPassword)

        // when
        val result = userService!!.findById(user.getId())

        // then
        assertNotEquals(rawPassword, result.getPassword())
        Assertions.assertTrue(customBCryptPasswordEncoder!!.matches(encodePassword!!, result.getPassword()))
    }
}