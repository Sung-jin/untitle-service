package com.example.demo.entity.order

import com.example.demo.entity.user.User
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import java.util.function.Consumer
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
class Order {
    @Id
    @GeneratedValue
    private val id: Long = 0

    @ManyToOne(optional = false)
    private val orderUser: User? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    private val orderList: List<OrderProduct>? = null

    fun prepareSave(): Order {

        orderList!!.forEach(Consumer { orderProduct: OrderProduct -> orderProduct.order = this })
        return this
    }
}