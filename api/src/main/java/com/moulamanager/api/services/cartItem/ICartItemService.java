package com.moulamanager.api.services.cartItem;

import com.moulamanager.api.dto.CartItemResultDTO;
import com.moulamanager.api.models.CartItemModel;

import java.util.List;

public interface ICartItemService {

    List<CartItemModel> findAll();

    CartItemModel findById(long id);

    CartItemModel findByCartId(long cartId);

    CartItemModel findByProductId(long productId);

    CartItemResultDTO addProductToCart(long productId, String token);

    CartItemModel save(CartItemModel cartItem);

    CartItemModel update(CartItemModel cartItem);

    void delete(long id);

}
