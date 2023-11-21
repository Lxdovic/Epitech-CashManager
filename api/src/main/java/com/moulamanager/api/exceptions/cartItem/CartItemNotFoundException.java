package com.moulamanager.api.exceptions.cartItem;

public class CartItemNotFoundException extends RuntimeException{

    public CartItemNotFoundException(String message) {
        super(message);
    }
}
