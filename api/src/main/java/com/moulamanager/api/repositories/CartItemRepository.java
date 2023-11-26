package com.moulamanager.api.repositories;

import com.moulamanager.api.models.CartItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemModel, Long> {

    Optional<CartItemModel> findById(long id);
    Optional<CartItemModel> findByCartId(long cartId);
    Optional<CartItemModel> findByProductId(long productId);

    Optional<CartItemModel>findByCartIdAndProductId(long cartId, long productId);

    boolean existsById(long id);

    boolean existsByCartId(long cartId);

    boolean existsByProductId(long productId);

    boolean existsByCartIdAndProductId(long cartId, long productId);

    void deleteAllByCartId(long cartId);


}
