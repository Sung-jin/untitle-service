package com.example.demo.order.domain

import com.example.demo.product.domain.Product
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class OrderProduct (
    @Id
    @GeneratedValue
    val id: Long? = null,

    @OneToOne(optional = false)
    var product: Product
) {
    @ManyToOne(optional = false)
    @JsonIgnore
    lateinit var order: Order
}