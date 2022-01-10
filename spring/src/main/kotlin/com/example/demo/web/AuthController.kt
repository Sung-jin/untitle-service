package com.example.demo.web

import com.example.demo.security.jwt.AuthenticationSecurityService
import com.example.demo.security.jwt.JwtTokenProvider
import com.example.demo.web.dto.Login
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationSecurityService: AuthenticationSecurityService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @GetMapping("/key")
    fun saltKey(): Map<String, String> = mapOf(
        "key" to authenticationSecurityService.saltKey
    )

    @PostMapping("/login")
    fun login(@RequestBody login: Login): String {
        return jwtTokenProvider.generateToken(login)
    }
}