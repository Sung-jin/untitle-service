package com.example.demo.service

import com.example.demo.config.annotation.LocalBootTest
import com.example.demo.entity.product.Product
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors

@LocalBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    lateinit var productService: ProductService

    @Test
    @DisplayName("상품 단일/리스트 조회")
    fun saveUserTest() {
        // given
        val products = listOf(
            Product(name = "상품이름 1", price = 10000),
            Product(name = "상품이름 2", price = 20000),
            Product(name = "상품이름 3", price = 30000)
        ).map {
            productService.save(it)
        }

        // when
        val productResult = productService.findById(products[0].id ?: fail("mock 상품 저장 실패"))
        val productsResult = productService.findAll()
        val productIds = products.map { it.id }
        val productResultIds = productsResult.map { it.id }

        // then
        assertEquals(products[0].id, productResult?.id)
        assertTrue(productResultIds.containsAll(productIds))
    }
}