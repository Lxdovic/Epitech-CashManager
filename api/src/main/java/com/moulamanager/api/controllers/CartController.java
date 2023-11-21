package com.moulamanager.api.controllers;

import com.moulamanager.api.models.CartModel;
import com.moulamanager.api.services.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartModel>> getAllCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }
}
