package com.example.demo.entity.order

import com.example.demo.entity.product.Product
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