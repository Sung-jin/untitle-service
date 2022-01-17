package com.example.demo.product.service

import com.example.demo.product.domain.Product
import com.example.demo.product.domain.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    fun findById(id: Long): Product? {
        return productRepository.findById(id).orElse(null)
    }

    fun findAllByIds(ids: List<Long>): List<Product> {
        return productRepository.findAllById(ids)
    }

    @Transactional
    fun save(product: Product): Product {
        return productRepository.save(product)
    }
}