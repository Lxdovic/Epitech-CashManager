package com.moulamanager.api.exceptions.cartItem;

public class CartItemAlreadyExistsException extends RuntimeException{

    public CartItemAlreadyExistsException(String message) {
        super(message);
    }
}
