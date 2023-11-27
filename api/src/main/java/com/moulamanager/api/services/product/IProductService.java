package com.moulamanager.api.services.product;

import com.moulamanager.api.models.ProductModel;

import java.util.List;

public interface IProductService {
    List<ProductModel> findAll();
    ProductModel findById(long id);

    ProductModel findByBarcode(String barcode);
    ProductModel save(ProductModel product);

    ProductModel update(ProductModel product);

    void delete(long id);
}
