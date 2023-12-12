package com.moulamanager.api.services.cart;

import com.moulamanager.api.dto.cart.result.CartResultDTO;
import com.moulamanager.api.models.CartModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICartService {

    Page<CartResultDTO> findAll(Pageable pageable);

    CartResultDTO findById(long id);

    CartResultDTO findByUserId(long userId);

    CartResultDTO findByUserIdAndCheckedOut(long userId, boolean checkedOut);

    CartResultDTO save(long userId);

    CartResultDTO update(CartModel cart);

    void delete(long id);
}
