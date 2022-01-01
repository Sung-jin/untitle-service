package com.example.demo.service;

import com.example.demo.entity.order.Order;
import com.example.demo.entity.order.OrderProduct;
import com.example.demo.entity.product.Product;
import com.example.demo.entity.user.User;
import com.example.demo.repo.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findAllOrderByLoginUser() {
        User user = userService.loginUserPrincipal().getUser();

        if (user == null) {
            log.warn("User info not found");
            return null;
        } else {
            return orderRepository.findAllOrderByOrderUserId(user.getId());
        }
    }

    @Transactional
    public Order orderProducts(List<Long> productIds) {
        User user = userService.loginUserPrincipal().getUser();

        if (user == null) {
            log.warn("User info not found");
            return null;
        } else {
            List<Product> products = productService.findAllByIds(productIds);
            List<OrderProduct> orderProducts = products
                    .stream()
                    .map(product -> OrderProduct.builder().product(product).build())
                    .collect(Collectors.toList());

            return orderRepository.save(
                    Order.builder()
                            .orderUser(user)
                            .orderList(orderProducts)
                            .build()
                    .prepareSave()
            );
        }
    }
}
