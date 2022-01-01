package com.example.demo.web;

import com.example.demo.entity.user.User;
import com.example.demo.security.AuthenticationSecurityService;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationSecurityService authenticationSecurityService;

    public UserController(UserService userService, AuthenticationSecurityService authenticationSecurityService) {
        this.userService = userService;
        this.authenticationSecurityService = authenticationSecurityService;
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/session-key")
    public Map<String, String> getSaltKey() {
        return Map.of(
            "key", authenticationSecurityService.saltKey()
        );
    }

    @PostMapping("/join")
    public User join(@RequestBody User user) throws Exception {
        return userService.save(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws Exception {
        return userService.save(user);
    }
}
