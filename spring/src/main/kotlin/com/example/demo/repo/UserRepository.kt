package com.example.demo.repo

import com.example.demo.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findUserByLoginId(loginId: String): User
}