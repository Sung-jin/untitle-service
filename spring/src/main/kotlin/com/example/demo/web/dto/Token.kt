package com.example.demo.web.dto

data class Token (
    val request: Request = Request(),
    val response: Response = Response()
) {
    data class Request (
        var id: String? = null,
        var secret: String? = null
    )

    data class Response (
        var token: String? = null
    )
}