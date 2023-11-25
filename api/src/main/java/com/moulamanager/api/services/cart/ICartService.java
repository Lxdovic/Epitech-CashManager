package com.moulamanager.api.services.cart;

import com.moulamanager.api.dto.CartResultDTO;
import com.moulamanager.api.models.CartModel;

import java.util.List;

public interface ICartService {

    List<CartResultDTO> findAll();

    CartResultDTO findById(long id);

    CartResultDTO findByUserId(long userId);

    CartResultDTO findByUserIdAndCheckedOut(long userId, boolean checkedOut);

    CartResultDTO save(long userId);

    CartResultDTO update(CartModel cart);

    void delete(long id);
}
