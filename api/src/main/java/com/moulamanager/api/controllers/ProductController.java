package com.moulamanager.api.controllers;

import com.moulamanager.api.models.ProductModel;
import com.moulamanager.api.services.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends AbstractController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductModel> getProductByBarcode(@PathVariable String barcode) {
        return ResponseEntity.ok(productService.findByBarcode(barcode));
    }

    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable long id, @RequestBody ProductModel product) {
        product.setId(id);
        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}
