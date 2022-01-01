package com.example.demo.service;

import com.example.demo.config.annotation.LocalBootTest;
import com.example.demo.entity.user.User;
import com.example.demo.generator.MockUserBuilder;
import com.example.demo.security.CustomBCryptPasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@LocalBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomBCryptPasswordEncoder customBCryptPasswordEncoder;

    @Autowired
    private MockUserBuilder mockUserBuilder;

    private final String rawPassword = "password";

    @Test
    @DisplayName("회원 가입/조회 테스트")
    void saveUserTest() throws Exception {
        // given
        User user = mockUserBuilder.build("demo", "email@demo.com", rawPassword);

        // when
        User result = userService.findById(user.getId());

        // then
        assertNotNull(result);
        assertEquals(user.getLoginId(), result.getLoginId());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void userPasswordEncryptionTest() throws Exception {
        // given
        User user = mockUserBuilder.build("demo", "email@demo.com", rawPassword);
        String encodePassword = mockUserBuilder.uiEncode(rawPassword);

        // when
        User result = userService.findById(user.getId());

        // then
        assertNotEquals(rawPassword, result.getPassword());
        assertTrue(customBCryptPasswordEncoder.matches(encodePassword, result.getPassword()));
    }
}
