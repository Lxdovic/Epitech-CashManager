package com.moulamanager.api.controllers;

import com.moulamanager.api.dto.CartCreationResultDTO;
import com.moulamanager.api.models.CartModel;
import com.moulamanager.api.services.cart.CartService;
import com.moulamanager.api.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
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
    public ResponseEntity<CartCreationResultDTO> createCart(@RequestParam long userId) {
        return ResponseEntity.ok(cartService.save(userId));
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
