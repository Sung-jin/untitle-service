package com.example.demo.config.filter

import mu.KLogging
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler: AccessDeniedHandler {
    @Throws(IOException::class, ServletException::class)
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        logger.error("Access denied because don't have the required permissions.", accessDeniedException)

        response.sendError(HttpServletResponse.SC_FORBIDDEN)
    }

    companion object: KLogging()
}