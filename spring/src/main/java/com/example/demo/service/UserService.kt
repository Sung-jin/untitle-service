package com.example.demo.service;

import com.example.demo.entity.user.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.security.AuthenticationSecurityService;
import com.example.demo.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationSecurityService authenticationSecurityService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationSecurityService authenticationSecurityService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationSecurityService = authenticationSecurityService;
    }

    public UserPrincipal loginUserPrincipal() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByLoginId(String loginId) {
        return userRepository.findUserByLoginId(loginId);
    }

    @Transactional
    public User save(User user) throws Exception {
        if (user.getSavePassword() != null) {
            user.setPassword(
                    passwordEncoder.encode(
                            authenticationSecurityService.decrypt(user.getSavePassword())
                    )
            );
        } else {
            user.setPassword(
                    userRepository.getById(user.getId()).getPassword()
            );
        }

        return userRepository.save(user);
    }
}
