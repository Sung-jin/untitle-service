package com.example.demo.user.request

data class Login (
    val loginId: String,
    val encodedPassword: String
)