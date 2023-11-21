package com.moulamanager.api.controllers;

import com.moulamanager.api.models.CartModel;
import com.moulamanager.api.services.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CartModel> getCartById(@PathVariable long id) {
        return ResponseEntity.ok(cartService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CartModel> createCart(@RequestBody CartModel cart) {
        return ResponseEntity.ok(cartService.save(cart));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CartModel> updateCart(@RequestBody CartModel cart, @PathVariable long id) {
        cart.setId(id);
        return ResponseEntity.ok(cartService.update(cart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}
