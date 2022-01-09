package com.example.demo.entity.user

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.*

@Entity
data class User (
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false)
    val loginId: String,

    @Column(nullable = false)
    val email: String
): Serializable {
    @Column(nullable = false)
    @JsonIgnore
    var password: String? = null

    @Transient
    var savePassword: String? = null

    @Transient
    var accessToken: String? = null

    @Transient
    var refreshToken: String? = null

    @Transient
    var expiredTimeIn: Long? = null
}