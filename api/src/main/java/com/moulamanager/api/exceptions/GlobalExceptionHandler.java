package com.moulamanager.api.exceptions;

import com.moulamanager.api.exceptions.cart.CartAlreadyCheckedOutException;
import com.moulamanager.api.exceptions.cart.CartAlreadyExistsException;
import com.moulamanager.api.exceptions.cart.CartNotFoundException;
import com.moulamanager.api.exceptions.product.ProductAlreadyExistsException;
import com.moulamanager.api.exceptions.product.ProductNotFoundException;
import com.moulamanager.api.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
        return buildResponseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ProductAlreadyExistsException.class)
    public ResponseEntity<Object> handleProductAlreadyExistsException(ProductAlreadyExistsException exception) {
        return buildResponseException(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException exception) {
        return buildResponseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CartAlreadyExistsException.class)
    public ResponseEntity<Object> handleCartAlreadyExistsException(CartAlreadyExistsException exception) {
        return buildResponseException(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CartAlreadyCheckedOutException.class)
    public ResponseEntity<Object> handleCartAlreadyExistsException(CartAlreadyCheckedOutException exception) {
        return buildResponseException(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return buildResponseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return buildResponseException(exception, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private static ResponseEntity<Object> buildResponseException(RuntimeException exception, HttpStatus httpStatus) {
        Map<String, Object> body = buildBodyExceptionResponse(exception);
        return ResponseEntity
                .status(httpStatus)
                .body(body);
    }

    private static Map<String, Object> buildBodyExceptionResponse(Exception exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("message", exception.getMessage());
        return body;
    }



}
