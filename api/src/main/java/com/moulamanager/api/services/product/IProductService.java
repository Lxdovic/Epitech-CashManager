package com.moulamanager.api.services.product;

import com.moulamanager.api.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<ProductModel> findAll(Pageable pageable);
    ProductModel findById(long id);

    ProductModel findByBarcode(String barcode);
    ProductModel save(ProductModel product);

    ProductModel update(ProductModel product);

    void delete(long id);
}
