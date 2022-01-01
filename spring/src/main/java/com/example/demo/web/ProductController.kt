package com.example.demo.web;

import com.example.demo.entity.product.Product;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findUserById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping()
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }
}
