package com.example.demo.service

import com.example.demo.entity.product.Product
import com.example.demo.repo.ProductRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class ProductService(private val productRepository: ProductRepository) {
    fun findAll(): List<Product?> {
        return productRepository.findAll()
    }

    fun findById(id: Long): Product {
        return productRepository.findById(id).orElse(null)!!
    }

    fun findAllByIds(ids: List<Long?>): List<Product?> {
        return productRepository.findAllById(ids)
    }

    @Transactional
    fun save(product: Product): Product {
        return productRepository.save(product)
    }
}