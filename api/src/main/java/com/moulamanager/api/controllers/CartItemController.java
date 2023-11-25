package com.moulamanager.api.controllers;

import com.moulamanager.api.dto.AddProductToCartRequestDTO;
import com.moulamanager.api.dto.CartItemResultDTO;
import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.services.cartItem.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CartItemModel> getCartItemById(@PathVariable long id) {
        return ResponseEntity.ok(cartItemService.findById(id));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CartItemResultDTO> addProductToCart(@PathVariable long productId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(cartItemService.addProductToCart(productId, token));
    }

}