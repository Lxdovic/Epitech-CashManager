package com.moulamanager.api.services.cart;

import com.moulamanager.api.models.CartModel;

import java.util.List;

public interface ICartService {

    List<CartModel> findAll();

    CartModel findById(long id);

    CartModel findByUserId(long userId);

    CartModel save(CartModel cart);

    CartModel update(CartModel cart);

    void delete(long id);
}
