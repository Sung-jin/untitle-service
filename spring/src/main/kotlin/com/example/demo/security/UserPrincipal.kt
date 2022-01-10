//package com.example.demo.security
//
//import com.example.demo.entity.user.User
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.userdetails.UserDetails
//
//class UserPrincipal (
//    val user: User? = null
//): UserDetails {
//    override fun getAuthorities(): Collection<GrantedAuthority?>? {
//        return null
//    }
//
//    override fun getPassword(): String {
//        return user?.password ?: ""
//    }
//
//    override fun getUsername(): String {
//        return user?.loginId ?: ""
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        return true
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isEnabled(): Boolean {
//        return true
//    }
//}