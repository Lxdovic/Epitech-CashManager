package com.moulamanager.api.services.cart;

import com.moulamanager.api.dto.cart.result.CartResultDTO;
import com.moulamanager.api.models.CartModel;

import java.util.List;

public interface ICartService {

    List<CartResultDTO> findAll();

    CartResultDTO findById(long id);

    CartResultDTO findByUserId(long userId);

    CartResultDTO findByUserIdAndCheckedOut(long userId, boolean checkedOut);

    CartResultDTO save(long userId);

    CartResultDTO update(CartModel cart);

    CartResultDTO updateCartTotalPrice(long cartId, double totalPrice);

    void delete(long id);
}
