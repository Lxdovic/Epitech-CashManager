package com.moulamanager.api.controllers;

import com.moulamanager.api.models.ProductModel;
import com.moulamanager.api.repositories.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping("/all")
    public List<ProductModel> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ProductModel> getProductById(@PathVariable long id) {
        return productRepository.findById(id);
    }

}
