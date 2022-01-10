//package com.example.demo.security
//
//import org.springframework.security.core.Authentication
//import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
//import org.springframework.stereotype.Component
//import java.io.IOException
//import javax.servlet.ServletException
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//@Component
//class LogoutSuccessHandler : SimpleUrlLogoutSuccessHandler() {
//    @Throws(IOException::class, ServletException::class)
//    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
//        response.status = HttpServletResponse.SC_OK
//        response.writer.flush()
//        defaultTargetUrl = "/sign"
//        super.onLogoutSuccess(request, response, authentication)
//    }
//}