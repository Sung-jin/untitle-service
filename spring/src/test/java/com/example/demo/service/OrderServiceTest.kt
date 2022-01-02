package com.example.demo.service

import com.example.demo.config.annotation.LocalBootTest
import com.example.demo.entity.order.Order
import com.example.demo.entity.product.Product
import com.example.demo.entity.user.User
import com.example.demo.generator.MockUserBuilder
import com.example.demo.repo.ProductRepository
import com.example.demo.repo.UserRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors

@LocalBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class OrderServiceTest {
    @Autowired
    private val orderService: OrderService? = null

    @Autowired
    private val productService: ProductService? = null

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val productRepository: ProductRepository? = null

    @Autowired
    private val mockUserBuilder: MockUserBuilder? = null

    private var testUser: User? = null
    private var products: List<Product>? = null

    @BeforeAll
    @Throws(Exception::class)
    fun setUp() {
        testUser = mockUserBuilder!!.build("demo", "email@demo.com", "password")
        products = listOf(
            Product(name = "상품이름 1", price = 10000),
            Product(name = "상품이름 2", price = 20000),
            Product(name = "상품이름 3", price = 30000)
        ).map {
            productService!!.save(it)
        }
    }

    @Test
    @DisplayName("상품 주문 테스트")
    @WithUserDetails("demo")
    fun orderProductTest() {
        // given
        val mockProductIds = products!!.map { it.id ?: fail("mock 상품 저장 실패") }
        val order = orderService!!.orderProducts(mockProductIds)

        // when
        val results = orderService.findAllOrderByLoginUser()
        val newOrder = results.first { it.orderUser.id == testUser?.id }
        val newOrderProductIds = newOrder.orderList.map { it.product.id }

        // then
        assertNotNull(order?.id)
        assertEquals(order?.id, newOrder.id)
        Assertions.assertTrue(mockProductIds.containsAll(newOrderProductIds))
    }

    @AfterAll
    fun teardown() {
        productRepository!!.deleteAllById(products!!.map{ it.id })
        userRepository!!.delete(testUser!!)
    }
}