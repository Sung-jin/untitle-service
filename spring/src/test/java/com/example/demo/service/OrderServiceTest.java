//package com.example.demo.service;
//
//import com.example.demo.config.annotation.LocalBootTest;
//import com.example.demo.entity.order.Order;
//import com.example.demo.entity.product.Product;
//import com.example.demo.entity.user.User;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@LocalBootTest
//@Transactional
//class OrderServiceTest {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private UserService userService;
//
//    private User testUser;
//    private List<Product> products;
//
//    @BeforeAll
//    void setUp() {
//        this.testUser = User.builder()
//                .loginId("demo")
//                .email("email@demo.com")
//                .savePassword("password")
//                .build();
//        products = Arrays.stream(
//                new Product[]{
//                        Product.builder().name("상품이름 1").price(10000).build(),
//                        Product.builder().name("상품이름 2").price(20000).build(),
//                        Product.builder().name("상품이름 3").price(30000).build()
//                }).map(product -> productService.save(product))
//                .collect(Collectors.toList());
//    }
//
//    @Test
//    @DisplayName("상품 주문 테스트")
//    @WithUserDetails("demo")
//    void saveUserTest() throws Exception {
//        // given
//        List<Long> mockProductIds = products.stream().map(Product::getId).collect(Collectors.toList());
//        Order order = orderService.orderProducts(mockProductIds);
//
//        // when
//        List<Order> results = orderService.findAll();
//        Order newOrder = results.stream().filter(orderByUser -> orderByUser.getOrderUser().getId().equals(testUser.getId())).findFirst().get();
//        List<Long> newOrderProductIds = newOrder.getOrderList().stream().map(o -> o.getProduct().getId()).collect(Collectors.toList());
//
//        // then
//        assertEquals(order.getId(), newOrder.getId());
//        assertTrue(mockProductIds.containsAll(newOrderProductIds));
//    }
//}
