package com.example.demo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("유저 저장 테스트")
    void saveUserTest() {
        // given
        User user = new User("email@demo.com", "password");

        // when
        User result = userService.save(user);

        // then
        assertEquals(user.email, result.email);
        assertEquals(user.password, result.password);
        // TODO - password 암호화 적용 시점에 password 비교 변경필요
    }

    @Test
    @DisplayName("유저 조회 테스트")
    void getUserTest() {
        // given
        User user = userService.save(new User("email@demo.com", "password"));

        // when
        User result = userService.get(user.id);

        // then
        assertEquals(user.email, result.email);
        assertEquals(user.password, result.password);
    }
}
