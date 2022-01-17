package com.example.demo.user.service

import com.example.demo.user.domain.User
import com.example.demo.user.domain.UserRepository
import com.example.demo.auth.service.AuthenticationSecurityService
//import com.example.demo.security.UserPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationSecurityService: AuthenticationSecurityService
) {
    fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun findByLoginId(loginId: String): User? {
        return userRepository.findUserByLoginId(loginId)
    }

    @Transactional
    @Throws(Exception::class)
    fun save(user: User): User {
        user.savePassword?.let {
            user.password = passwordEncoder.encode(
                authenticationSecurityService.decrypt(it)
            )
        } ?: run {
            user.password = userRepository.getById(user.id!!).password
        }

        return userRepository.save(user)
    }
}