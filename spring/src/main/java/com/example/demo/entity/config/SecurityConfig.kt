package com.example.demo.entity.config;

import com.example.demo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthService userAuthService;

    private LoginSuccessHandler loginSuccessHandler;

    private LoginFailureHandler loginFailureHandler;

    private LogoutSuccessHandler logoutSuccessHandler;

    private CustomBCryptPasswordEncoder customBCryptPasswordEncoder;

    public SecurityConfig(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Autowired
    public SecurityConfig(UserAuthService userAuthService, LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler, LogoutSuccessHandler logoutSuccessHandler, CustomBCryptPasswordEncoder customBCryptPasswordEncoder) {
        this.userAuthService = userAuthService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.customBCryptPasswordEncoder = customBCryptPasswordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(customBCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
                .logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
    }
}
