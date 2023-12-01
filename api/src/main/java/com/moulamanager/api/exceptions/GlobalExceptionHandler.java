package com.moulamanager.api.exceptions;

import com.moulamanager.api.exceptions.cart.CartAlreadyCheckedOutException;
import com.moulamanager.api.exceptions.cart.CartAlreadyExistsException;
import com.moulamanager.api.exceptions.cart.CartNotFoundException;
import com.moulamanager.api.exceptions.cartItem.CartItemAlreadyExistsException;
import com.moulamanager.api.exceptions.cartItem.CartItemNotFoundException;
import com.moulamanager.api.exceptions.cartItem.InvalidQuantityException;
import com.moulamanager.api.exceptions.product.ProductAlreadyExistsException;
import com.moulamanager.api.exceptions.product.ProductNotFoundException;
import com.moulamanager.api.exceptions.user.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
        logger.error("Product not found exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ProductAlreadyExistsException.class)
    public ResponseEntity<Object> handleProductAlreadyExistsException(ProductAlreadyExistsException exception) {
        logger.error("Product already exists exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException exception) {
        logger.error("Cart not found exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CartAlreadyExistsException.class)
    public ResponseEntity<Object> handleCartAlreadyExistsException(CartAlreadyExistsException exception) {
        logger.error("Cart already exists exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CartAlreadyCheckedOutException.class)
    public ResponseEntity<Object> handleCartAlreadyExistsException(CartAlreadyCheckedOutException exception) {
        logger.error("Cart already checked out exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        logger.error("User not found exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CartItemNotFoundException.class)
    public ResponseEntity<Object> handleCartItemNotFoundException(CartItemNotFoundException exception) {
        logger.error("Cart item not found exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CartItemAlreadyExistsException.class)
    public ResponseEntity<Object> handleCartItemAlreadyExistsException(CartItemAlreadyExistsException exception) {
        logger.error("Cart item already exists exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InvalidQuantityException.class)
    public ResponseEntity<Object> handleInvalidQuantityException(InvalidQuantityException exception) {
        logger.error("Invalid quantity exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        logger.error("Runtime exception: {}", exception.getMessage());
        return buildResponseException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        logger.error("Constraint violation exception: {}", exception.getMessage());
        String errorMessage = exception.getConstraintViolations().iterator().next().getMessage();
        return buildResponseException(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseException(String exception, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), exception, httpStatus.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }


}
