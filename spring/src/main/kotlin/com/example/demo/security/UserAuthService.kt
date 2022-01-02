package com.example.demo.security

import com.example.demo.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserAuthService(
    private val userService: UserService
) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(loginId: String): UserDetails {
        return userService.findByLoginId(loginId)?.let {
            UserPrincipal(it)
        } ?: throw UsernameNotFoundException("account is not found. loginId : $loginId")
    }
}