package com.moulamanager.api.exceptions.product;

public class InvalidNameLength extends RuntimeException {
    public InvalidNameLength(String message) {
        super(message);
    }
}
