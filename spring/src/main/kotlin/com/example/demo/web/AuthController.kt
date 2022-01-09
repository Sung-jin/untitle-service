package com.example.demo.web

import com.example.demo.security.AuthenticationSecurityService
import com.example.demo.service.UserService
import com.example.demo.web.dto.Token
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationSecurityService: AuthenticationSecurityService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: Token.Request) {

    }
}