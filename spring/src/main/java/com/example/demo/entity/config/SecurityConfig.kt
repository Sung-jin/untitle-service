package com.example.demo.entity.config

import com.example.demo.security.*
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import kotlin.Throws
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import java.lang.Exception

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
class SecurityConfig : WebSecurityConfigurerAdapter {
    private val userAuthService: UserAuthService
    private var loginSuccessHandler: LoginSuccessHandler? = null
    private var loginFailureHandler: LoginFailureHandler? = null
    private var logoutSuccessHandler: LogoutSuccessHandler? = null
    private var customBCryptPasswordEncoder: CustomBCryptPasswordEncoder? = null

    constructor(userAuthService: UserAuthService) {
        this.userAuthService = userAuthService
    }

    @Autowired
    constructor(userAuthService: UserAuthService, loginSuccessHandler: LoginSuccessHandler?, loginFailureHandler: LoginFailureHandler?, logoutSuccessHandler: LogoutSuccessHandler?, customBCryptPasswordEncoder: CustomBCryptPasswordEncoder?) {
        this.userAuthService = userAuthService
        this.loginSuccessHandler = loginSuccessHandler
        this.loginFailureHandler = loginFailureHandler
        this.logoutSuccessHandler = logoutSuccessHandler
        this.customBCryptPasswordEncoder = customBCryptPasswordEncoder
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        super.configure(web)
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userAuthService).passwordEncoder(customBCryptPasswordEncoder)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers(
                        "/users/join",
                        "/users/session-key"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
                .and()
                .logout().deleteCookies("remove").invalidateHttpSession(true)
                .logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler)
    }
}