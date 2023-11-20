package com.moulamanager.api.exception.product;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
