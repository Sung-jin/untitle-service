package com.example.demo.web

import com.example.demo.entity.order.Order
import com.example.demo.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {
    @GetMapping
    fun findAllOrderByLoginUser(@RequestParam token: String): List<Order> {
        return orderService.findAllOrderByLoginUser(token)
    }

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long): Order? {
        return orderService.findById(id)
    }

    @PostMapping
    fun orderProducts(@RequestParam token: String, @RequestBody productIds: List<Long>): Order? {
        return orderService.orderProducts(token, productIds)
    }
}
