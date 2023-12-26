package com.moulamanager.api.repositories;

import com.moulamanager.api.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    Optional<ProductModel> findById(long id);

    Optional<ProductModel> findByBarcode(String barcode);

    boolean existsById(long id);

    Page<ProductModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByBarcode(String barcode);


}