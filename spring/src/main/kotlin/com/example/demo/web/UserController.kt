package com.example.demo.web

import com.example.demo.entity.user.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long): User? {
        return userService.findById(id)
    }

    @PostMapping("/join")
    @Throws(Exception::class)
    fun join(@RequestBody user: User): User {
        return userService.save(user)
    }

    @PutMapping
    @Throws(Exception::class)
    fun updateUser(@RequestBody user: User): User {
        return userService.save(user)
    }
}