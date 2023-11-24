package com.moulamanager.api.services.cart;

import com.moulamanager.api.dto.CartCreationResultDTO;
import com.moulamanager.api.models.CartModel;

import java.util.List;

public interface ICartService {

    List<CartModel> findAll();

    CartModel findById(long id);

    CartModel findByUserId(long userId);

    CartCreationResultDTO save(long userId);

    CartModel update(CartModel cart);

    void delete(long id);
}
