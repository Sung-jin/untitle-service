package com.example.demo.entity.config

import com.example.demo.security.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
class SecurityConfig (
    private val loginSuccessHandler: LoginSuccessHandler,
    private val loginFailureHandler: LoginFailureHandler,
    private val logoutSuccessHandler: LogoutSuccessHandler,
    private val customBCryptPasswordEncoder: CustomBCryptPasswordEncoder,
    private val userAuthService: UserAuthService
) : WebSecurityConfigurerAdapter() {

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