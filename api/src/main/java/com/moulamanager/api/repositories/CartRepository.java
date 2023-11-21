package com.moulamanager.api.repositories;

import com.moulamanager.api.models.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<CartModel, Long> {

    Optional<CartModel> findById(long id);
    Optional<CartModel> findByUserId(long userId);

    boolean existsByUserId(long userId);
    boolean existsById(long id);

}