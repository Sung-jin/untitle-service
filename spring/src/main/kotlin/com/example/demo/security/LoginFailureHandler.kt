//package com.example.demo.security
//
//import mu.KLogging
//import org.springframework.security.core.AuthenticationException
//import org.springframework.security.web.authentication.AuthenticationFailureHandler
//import org.springframework.stereotype.Component
//import java.io.IOException
//import javax.servlet.ServletException
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//@Component
//class LoginFailureHandler : AuthenticationFailureHandler {
//    @Throws(IOException::class, ServletException::class)
//    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
//        logger.error("sign error: ", exception)
//        logger.error("request id: ${request.getParameter("username")}")
//        response.status = HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION
//        request.getSession(false).invalidate()
//    }
//
//    companion object : KLogging()
//}