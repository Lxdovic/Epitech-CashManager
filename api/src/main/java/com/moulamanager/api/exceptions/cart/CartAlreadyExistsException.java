package com.moulamanager.api.exceptions.cart;

public class CartAlreadyExistsException extends RuntimeException {

    public CartAlreadyExistsException(String message) {
        super(message);
    }
}
