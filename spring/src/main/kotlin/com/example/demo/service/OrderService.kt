package com.example.demo.service

import com.example.demo.entity.order.Order
import com.example.demo.entity.order.OrderProduct
import com.example.demo.entity.user.User
import com.example.demo.repo.OrderRepository
import com.example.demo.security.JwtTokenProvider
import mu.KLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val productService: ProductService
) {
    fun findById(id: Long): Order? {
        return orderRepository.findById(id).orElse(null)
    }

    fun findAllOrderByLoginUser(): List<Order> {
        return orderRepository.findAllOrderByOrderUserId(
            (SecurityContextHolder.getContext().authentication.principal as User).id!!
        )
    }

    @Transactional
    fun orderProducts(productIds: List<Long>): Order {
        val test = SecurityContextHolder.getContext().authentication.principal
        return orderRepository.save(
            Order(
                orderList = productService
                    .findAllByIds(productIds)
                    .map { product -> OrderProduct(product = product) },
                orderUser = (SecurityContextHolder.getContext().authentication.principal as User)
            ).prepareSave()
        )
    }

    companion object : KLogging()
}
