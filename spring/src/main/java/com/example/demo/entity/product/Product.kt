package com.example.demo.entity.product

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Product {
    @Id
    @GeneratedValue
    private val id: Long? = null

    @Column(nullable = false)
    private val name: String? = null

    @Column(nullable = false)
    private val price = 0
}