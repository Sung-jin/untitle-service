package com.example.demo.security

import mu.KLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter (
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            getJwtFromRequest(request)?.let { jwt ->
                if (jwtTokenProvider.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken(
                        jwtTokenProvider.getUserIdFromJWT(jwt), null, null
                    ).apply {
                        this.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = this
                    }
                } else {
                    request.setAttribute("Unauthorized", HttpServletResponse.SC_FORBIDDEN)
                }
            } ?: run {
                request.setAttribute("Unauthorized", HttpServletResponse.SC_UNAUTHORIZED)
            }
        } catch (e: Exception) {
            logger.error("Could not set user authentication in security context", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        return request.getHeader(AUTHORIZATION_HEADER).run {
            if (this.isNotEmpty() && this.startsWith(BEARER_PREFIX)) this.substring(BEARER_PREFIX.length) else null
        }
    }

    companion object: KLogging() {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}