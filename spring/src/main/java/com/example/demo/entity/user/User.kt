package com.example.demo.entity.user

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import java.io.Serializable
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
class User : Serializable {
    @Id
    @GeneratedValue
    private val id: Long? = null

    @Column(nullable = false)
    private val loginId: String? = null

    @Column(nullable = false)
    @JsonIgnore
    private var password: String? = null

    @Column(nullable = false)
    private val email: String? = null

    @Transient
    private val savePassword: String? = null
    fun setPassword(password: String?) {
        this.password = password
    }
}