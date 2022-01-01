package com.example.demo.service

import com.example.demo.entity.order.Order
import com.example.demo.entity.order.OrderProduct
import com.example.demo.entity.product.Product
import com.example.demo.repo.OrderRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Function
import java.util.stream.Collectors

@Service
@Slf4j
class OrderService(private val orderRepository: OrderRepository, private val userService: UserService, private val productService: ProductService) {
    fun findById(id: Long): Order {
        return orderRepository.findById(id).orElse(null)!!
    }

    fun findAllOrderByLoginUser(): List<Order?>? {
        val user = userService.loginUserPrincipal().user
        return if (user == null) {
            OrderService.log.warn("User info not found")
            null
        } else {
            orderRepository.findAllOrderByOrderUserId(user.getId())
        }
    }

    @Transactional
    fun orderProducts(productIds: List<Long?>): Order? {
        val user = userService.loginUserPrincipal().user
        return if (user == null) {
            OrderService.log.warn("User info not found")
            null
        } else {
            val products = productService.findAllByIds(productIds)
            val orderProducts: List<OrderProduct> = products
                    .stream()
                    .map(Function<Product?, Any> { product: Product? -> OrderProduct.builder().product(product).build() })
                    .collect(Collectors.toList())
            orderRepository.save(
                    Order.builder()
                            .orderUser(user)
                            .orderList(orderProducts)
                            .build()
                            .prepareSave()
            )
        }
    }
}