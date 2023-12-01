package com.moulamanager.api.repositories;

import com.moulamanager.api.models.CartItemModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemModel, Long> {

    Page<CartItemModel> findAllByCartId(long cartId, Pageable pageable);

    Optional<CartItemModel>findByCartIdAndProductId(long cartId, long productId);

    boolean existsByCartIdAndProductId(long cartId, long productId);

    void deleteAllByCartId(long cartId);

    boolean existsByCartId(long cartId);
}
