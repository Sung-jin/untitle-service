package com.example.demo.config.annotation

import com.example.demo.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory
import org.springframework.stereotype.Component

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory::class)
annotation class JwtLoginUser(
    val loginId: String = "user"
)

@Component
class WithMockCustomUserSecurityContextFactory: WithSecurityContextFactory<JwtLoginUser> {
    @Autowired
    lateinit var userRepository: UserRepository

    override fun createSecurityContext(user: JwtLoginUser): SecurityContext {
        val context: SecurityContext = SecurityContextHolder.createEmptyContext()
        context.authentication = UsernamePasswordAuthenticationToken(
            userRepository.findUserByLoginId(user.loginId),
            null,
            null
        // 추후에 role 추가 시 대응 필요
        )

        return context
    }
}