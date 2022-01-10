//package com.example.demo.security
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.springframework.security.core.Authentication
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache
//import org.springframework.security.web.savedrequest.RequestCache
//import org.springframework.stereotype.Component
//import org.springframework.util.StringUtils
//import java.io.IOException
//import javax.servlet.ServletException
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//@Component
//class LoginSuccessHandler(
//    private val mapper: ObjectMapper
//) : SimpleUrlAuthenticationSuccessHandler() {
//    private val requestCache: RequestCache = HttpSessionRequestCache()
//
//    @Throws(IOException::class, ServletException::class)
//    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
//        setAuthentication(response, authentication)
//        val savedRequest = requestCache.getRequest(request, response)
//        val targetUrlParameter = targetUrlParameter
//
//        if (savedRequest == null) {
//            clearAuthenticationAttributes(request)
//            return
//        }
//
//        if (isAlwaysUseDefaultTargetUrl || targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter))) {
//            requestCache.removeRequest(request, response)
//            clearAuthenticationAttributes(request)
//            return
//        }
//
//        clearAuthenticationAttributes(request)
//    }
//
//    @Throws(IOException::class)
//    private fun setAuthentication(response: HttpServletResponse, authentication: Authentication) {
//        response.contentType = "application/json"
//        response.characterEncoding = "UTF-8"
//        response.writer.write(mapper.writeValueAsString(authentication.principal))
//        response.writer.flush()
//        response.writer.close()
//    }
//}