package com.example.demo.auth.controller

import com.example.demo.auth.service.AuthenticationSecurityService
import com.example.demo.auth.service.JwtTokenProvider
import com.example.demo.user.request.Login
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

    @PutMapping("/logout")
    fun logout() {
        // TODO - 추후에 refresh 토큰 세션 저장등의 로직이 추가되거나
        // logout 관련해서 작업이 필요하면 해당 api 를 통해서 작업해야 한다
    }
}
