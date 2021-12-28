package com.example.demo.service;

import com.example.demo.config.annotation.LocalBootTest;
import com.example.demo.entity.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@LocalBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 단일/리스트 조회")
    void saveUserTest() throws Exception {
        // given
        List<Product> products = productService.save(
                Arrays.stream(new Product[]{
                        Product.builder().name("상품이름 1").price(10000),
                        Product.builder().name("상품이름 2").price(20000),
                        Product.builder().name("상품이름 3").price(30000)
                }).map(product -> productService.save(product))
        );

        // when
        Product productResult = productService.findById(products.get(0).getId());
        List<Product> productsResult = productService.findAll();
        List<Long> productIds = products.stream().map(Product::getId).collect(Collectors.toList());
        List<Long> productResultIds = productsResult.stream().map(Product::getId).collect(Collectors.toList());

        // then
        assertEquals(products.get(0).getId(), productResult.getId());
        assertTrue(productResultIds.containsAll(productIds));
    }
}
