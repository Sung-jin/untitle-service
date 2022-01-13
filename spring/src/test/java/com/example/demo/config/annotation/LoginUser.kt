package com.example.demo.config.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class JwtLoginUser(
    val loginId: String = "user"
)
