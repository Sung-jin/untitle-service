package com.example.demo.entity.order

import com.example.demo.entity.product.Product
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
class OrderProduct {
    @Id
    @GeneratedValue
    private val id: Long? = null

    @ManyToOne(optional = false)
    @JsonIgnore
    private var order: Order? = null

    @OneToOne(optional = false)
    private val product: Product? = null
    fun setOrder(order: Order?) {
        this.order = order
    }
}