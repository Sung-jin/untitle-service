package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomBCryptPasswordEncoder extends BCryptPasswordEncoder {

    private final AuthenticationSecurityService authenticationSecurityService;

    public CustomBCryptPasswordEncoder(AuthenticationSecurityService authenticationSecurityService) {
        this.authenticationSecurityService = authenticationSecurityService;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return super.matches(
                    authenticationSecurityService.decrypt(rawPassword.toString()),
                    encodedPassword
            );
        } catch (Exception e) {
            log.error("password decode fail", e);
            e.printStackTrace();

            return false;
        }
    }
}
