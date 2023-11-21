package com.moulamanager.api.controllers;

import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.services.cartItem.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carts/items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemModel>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }
}
