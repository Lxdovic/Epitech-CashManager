package com.moulamanager.api.services.product;

import com.moulamanager.api.exceptions.product.ProductAlreadyExistsException;
import com.moulamanager.api.exceptions.product.ProductNotFoundException;
import com.moulamanager.api.models.ProductModel;
import com.moulamanager.api.repositories.ProductRepository;
import com.moulamanager.api.services.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends AbstractService<ProductModel> implements IProductService {

    private final ProductRepository productRepository;

    private final String PRODUCT_NOT_FOUND = "Product not found";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductModel findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductModel save(ProductModel product) {
        if (productRepository.existsByName(product.getName())) {
            String PRODUCT_ALREADY_EXISTS = "Product already exists";
            throw new ProductAlreadyExistsException(PRODUCT_ALREADY_EXISTS);
        }
        return productRepository.save(product);
    }

    @Override
    public ProductModel update(ProductModel product) {
        ProductModel productModel = productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        BeanUtils.copyProperties(product, productModel, getNullPropertyNames(product));
        return productRepository.save(productModel);
    }

    @Override
    public void delete(long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
    }
}
