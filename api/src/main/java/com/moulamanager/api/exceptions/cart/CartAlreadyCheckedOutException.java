package com.moulamanager.api.exceptions.cart;

public class CartAlreadyCheckedOutException extends RuntimeException {

    public CartAlreadyCheckedOutException(String message) {
        super(message);
    }
}
