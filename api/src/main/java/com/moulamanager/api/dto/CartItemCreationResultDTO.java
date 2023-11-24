package com.moulamanager.api.dto;

import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.models.ProductModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemCreationResultDTO {
    private long id;
    private CartCreationResultDTO cart;
    private ProductModel product;
    private int quantity;

    public static CartItemCreationResultDTO fromCartItemModel(CartItemModel cartItem) {
        return CartItemCreationResultDTO.builder()
                .id(cartItem.getId())
                .cart(CartCreationResultDTO.fromCartModel(cartItem.getCart()))
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
