package com.example.demo.order.domain

import com.example.demo.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "`order`")
data class Order (
    @Id
    @GeneratedValue
    val id: Long = 0,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    val orderList: List<OrderProduct> = mutableListOf(),

    @ManyToOne(optional = false)
    var orderUser: User
) {
    fun prepareSave(): Order {
        orderList.forEach {
            it.order = this
        }

        return this
    }
}