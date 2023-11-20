package com.moulamanager.api.repositories;

import com.moulamanager.api.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    Optional<ProductModel> findById(long id);
    Boolean existsById(long id);
    Optional<ProductModel> findByName(String name);
    Boolean existsByName(String name);






}