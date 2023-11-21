package com.moulamanager.api.exceptions.cart;

public class CartNotFoundException extends RuntimeException{

        public CartNotFoundException(String message) {
            super(message);
        }
}
