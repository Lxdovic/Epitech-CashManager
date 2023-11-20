package com.moulamanager.api.services.product;

import com.moulamanager.api.exception.product.ProductAlreadyExistsException;
import com.moulamanager.api.exception.product.ProductNotFoundException;
import com.moulamanager.api.models.ProductModel;
import com.moulamanager.api.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductModel findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public ProductModel save(ProductModel product) {
        if (productRepository.existsByName(product.getName())) {
            throw new ProductAlreadyExistsException("Product already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public ProductModel update(ProductModel product) {
        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFoundException("Product not found");
        }
        return productRepository.save(product);
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
