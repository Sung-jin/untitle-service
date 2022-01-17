package com.example.demo.order.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllOrderByOrderUserId(userId: Long): List<Order>
}