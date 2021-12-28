package com.example.demo.web;

import com.example.demo.entity.order.Order;
import com.example.demo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> findAllOrderByLoginUser() {
        return orderService.findAllOrderByLoginUser();
    }

    @GetMapping("/{id}")
    public Order findUserById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping()
    public Order orderProducts(@RequestBody List<Long> productIds) {
        return orderService.orderProducts(productIds);
    }
}
