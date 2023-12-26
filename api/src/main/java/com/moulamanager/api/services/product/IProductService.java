package com.moulamanager.api.services.product;

import com.moulamanager.api.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProductService {
    Page<ProductModel> findAll(Pageable pageable);
    ProductModel findById(long id);

    Page<ProductModel> findByName(String name, Pageable pageable);

    ProductModel findByBarcode(String barcode);
    ProductModel save(ProductModel product);

    ProductModel update(ProductModel product);

    void delete(long id);
}
