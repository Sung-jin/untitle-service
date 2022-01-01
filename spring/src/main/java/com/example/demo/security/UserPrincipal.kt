package com.example.demo.security

import com.example.demo.entity.user.User
import lombok.AllArgsConstructor
import lombok.Getter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@AllArgsConstructor
@Getter
class UserPrincipal : UserDetails {
    private val user: User? = null
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return null
    }

    override fun getPassword(): String {
        return user.getPassword()
    }

    override fun getUsername(): String {
        return user.getLoginId()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}