package com.moulamanager.api.dto;

import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.models.ProductModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartItemResultDTO {
    private long id;
    private CartResultDTO cart;
    private ProductModel product;
    private int quantity;

    public static CartItemResultDTO fromCartItemModel(CartItemModel cartItem) {
        return CartItemResultDTO.builder()
                .id(cartItem.getId())
                .cart(CartResultDTO.fromCartModel(cartItem.getCart()))
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static List<CartItemResultDTO> fromCartItemModelList(List<CartItemModel> cartItems) {
        return cartItems.stream().map(CartItemResultDTO::fromCartItemModel).toList();
    }

    public static CartItemModel toCartItemModel(CartItemResultDTO cartItem) {
        return CartItemModel.builder()
                .id(cartItem.getId())
                .cart(CartResultDTO.toCartModel(cartItem.getCart()))
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .build();
    }
}