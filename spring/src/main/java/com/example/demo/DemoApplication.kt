package com.example.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
object DemoApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(DemoApplication::class.java, *args)
    }
}