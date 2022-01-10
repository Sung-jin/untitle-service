package com.example.demo.service

import com.example.demo.entity.order.Order
import com.example.demo.entity.order.OrderProduct
import com.example.demo.repo.OrderRepository
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userService: UserService,
    private val productService: ProductService
) {
    fun findById(id: Long): Order? {
        return orderRepository.findById(id).orElse(null)
    }

    fun findAllOrderByLoginUser(): List<Order> {
//        val user = userService.loginUserPrincipal().user
//
//        return user?.id?.let {
//            orderRepository.findAllOrderByOrderUserId(it)
//        } ?: run {
//            logger.warn("User info not found")
//            listOf()
//        }
        return listOf()
    }

    @Transactional
    fun orderProducts(productIds: List<Long>): Order? {
//        val user = userService.loginUserPrincipal().user
//
//        return user?.let {
//            orderRepository.save(
//                Order(
//                    orderList = productService
//                        .findAllByIds(productIds)
//                        .map { product -> OrderProduct(product = product) },
//                    orderUser = it
//                ).prepareSave()
//            )
//        } ?: run {
//            logger.warn("User info not found")
//            null
//        }
        return null
    }

    companion object : KLogging()
}