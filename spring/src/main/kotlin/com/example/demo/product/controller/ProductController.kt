package com.example.demo.product.controller

import com.example.demo.product.domain.Product
import com.example.demo.product.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun findAll(): List<Product> {
        return productService.findAll()
    }

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long): Product? {
        return productService.findById(id)
    }

    @PostMapping
    fun save(@RequestBody product: Product): Product {
        return productService.save(product)
    }
}