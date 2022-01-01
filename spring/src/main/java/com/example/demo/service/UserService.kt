package com.example.demo.service

import com.example.demo.entity.user.User
import com.example.demo.repo.UserRepository
import com.example.demo.security.AuthenticationSecurityService
import com.example.demo.security.UserPrincipal
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder, private val authenticationSecurityService: AuthenticationSecurityService) {
    fun loginUserPrincipal(): UserPrincipal {
        return SecurityContextHolder.getContext().authentication.principal as UserPrincipal
    }

    fun findById(id: Long): User {
        return userRepository.findById(id).orElse(null)!!
    }

    fun findByLoginId(loginId: String?): User? {
        return userRepository.findUserByLoginId(loginId)
    }

    @Transactional
    @Throws(Exception::class)
    fun save(user: User): User {
        if (user.getSavePassword() != null) {
            user.setPassword(
                    passwordEncoder.encode(
                            authenticationSecurityService.decrypt(user.getSavePassword())
                    )
            )
        } else {
            user.setPassword(
                    userRepository.getById(user.getId()).getPassword()
            )
        }
        return userRepository.save(user)
    }
}