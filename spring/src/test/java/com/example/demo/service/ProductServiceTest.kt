package com.example.demo.service

import com.example.demo.config.annotation.LocalBootTest
import com.example.demo.entity.product.Product
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors

@LocalBootTest
@Transactional
internal class ProductServiceTest {
    @Autowired
    private val productService: ProductService? = null
    @Test
    @DisplayName("상품 단일/리스트 조회")
    fun saveUserTest() {
        // given
        val products = Arrays.stream(arrayOf<Product>(
                Product.builder().name("상품이름 1").price(10000).build(),
                Product.builder().name("상품이름 2").price(20000).build(),
                Product.builder().name("상품이름 3").price(30000).build()
        )).map { product: Product? -> productService!!.save(product!!) }
                .collect(Collectors.toList())

        // when
        val productResult = productService!!.findById(products[0].getId())
        val productsResult = productService.findAll()
        val productIds = products.stream().map<Any>(Product::getId).collect(Collectors.toList<Any>())
        val productResultIds = productsResult.stream().map<Any>(Product::getId).collect(Collectors.toList<Any>())

        // then
        assertEquals(products[0].getId(), productResult.getId())
        Assertions.assertTrue(productResultIds.containsAll(productIds))
    }
}