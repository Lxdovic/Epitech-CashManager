package com.moulamanager.api.dto;

import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.models.ProductModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResultDTO {
    private long id;
    private CartCreationResultDTO cart;
    private ProductModel product;
    private int quantity;

    public static CartItemResultDTO fromCartItemModel(CartItemModel cartItem) {
        return CartItemResultDTO.builder()
                .id(cartItem.getId())
                .cart(CartCreationResultDTO.fromCartModel(cartItem.getCart()))
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
