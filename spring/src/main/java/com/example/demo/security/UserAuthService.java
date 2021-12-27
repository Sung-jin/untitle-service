package com.example.demo.security;

import com.example.demo.entity.user.User;
import com.example.demo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    @Lazy
    private final UserService userService;

    public UserAuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userService.findByLoginId(loginId);

        if (user == null) throw new UsernameNotFoundException("account is not found. loginId : " + loginId);
        else return new UserPrincipal(user);
    }
}
