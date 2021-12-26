package com.example.demo.service;

import com.example.demo.config.annotation.LocalBootTest;
import com.example.demo.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@LocalBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("유저 저장 테스트")
    void saveUserTest() {
        // given
        User user = User
                .builder()
                .email("email@demo.com")
                .password("password")
                .build();

        // when
        User result = userService.save(user);

        // then
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        // TODO - password 암호화 적용 시점에 password 비교 변경필요
    }

    @Test
    @DisplayName("유저 조회 테스트")
    void getUserTest() {
        // given
        User user = userService.save(
                User.builder()
                        .email("email@demo.com")
                        .password("password")
                        .build()
        );

        // when
        User result = userService.findById(user.getId());

        // then
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
    }
}
