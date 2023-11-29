package com.moulamanager.api.controllers;

import com.moulamanager.api.dto.cartItem.result.CartItemResultDTO;
import com.moulamanager.api.dto.cartItem.request.UpdateCartItemQuantityDTO;
import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.services.cartItem.CartItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts/items")
public class CartItemController {

    private final CartItemService cartItemService;


    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    /**
     * Gets all cart items.
     * <p>Example:</p>
     * <pre>
     *     GET /carts/items
     *     Headers: Authorization: Bearer userToken
     * </pre>
     *
     * @return A {@link ResponseEntity} containing a page of {@link CartItemModel}s.
     */
    @GetMapping
    public ResponseEntity<Page<CartItemResultDTO>> getAllCartItems(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cartItemService.findAll(pageable));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<CartItemResultDTO>> getAllCartItemsByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestHeader("Authorization") String userToken) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cartItemService.findAllByUser(pageable, userToken));
    }

    /**
     * Gets a cart item by ID.
     * <p>Example:</p>
     * <pre>
     *     GET /carts/items/1
     *     Headers: Authorization: Bearer userToken
     * </pre>
     *
     * @param id The ID of the cart item to get.
     * @return A {@link ResponseEntity} containing the {@link CartItemModel} of the cart item.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartItemResultDTO> getCartItemById(@PathVariable long id) {
        return ResponseEntity.ok(cartItemService.findById(id));
    }

    /**
     * Adds a product to the cart.
     *
     * <p>Example:</p>
     * <pre>
     * POST /carts/items/1
     * Headers: Authorization: Bearer userToken;
     * </pre>
     *
     * @param productId The ID of the product to add to the cart.
     * @param userToken The token of the user who is adding the product.
     * @return A {@link ResponseEntity} containing the {@link CartItemResultDTO} of the added product.
     */
    @PostMapping("/{productId}")
    public ResponseEntity<CartItemResultDTO> addProductToCart(@PathVariable long productId, @RequestHeader("Authorization") String userToken) {
        return ResponseEntity.ok(cartItemService.addProductToCart(productId, userToken));
    }

    @PostMapping("/barcode/{barcode}")
    public ResponseEntity<CartItemResultDTO> addProductToCartWithBarcode(@PathVariable String barcode, @RequestHeader("Authorization") String userToken) {
        return ResponseEntity.ok(cartItemService.addProductToCartWithBarcode(barcode, userToken));
    }


    /**
     * Updates the quantity of a product in the cart.
     *
     * <p>Example:</p>
     * <pre>
     * PATCH /carts/items/1
     * Headers: Authorization: Bearer userToken
     * Body: {
     *     "quantity": 2
     * }
     * </pre>
     *
     * @param productId The ID of the product to update.
     * @param quantity  The new quantity of the product.
     * @param userToken The token of the user who is updating the product.
     * @return A {@link ResponseEntity} containing the {@link CartItemModel} of the updated product.
     */
    @PatchMapping("/{productId}")
    public ResponseEntity<CartItemResultDTO> updateProductQuantity(@PathVariable long productId, @RequestBody UpdateCartItemQuantityDTO quantity, @RequestHeader("Authorization") String userToken) {
        return ResponseEntity.ok(cartItemService.updateProductQuantity(productId, quantity, userToken));
    }

    /**
     * Removes a product from the cart.
     *
     * <p>Example:</p>
     * <pre>
     * DELETE /carts/items/1
     * Headers: Authorization: Bearer userToken
     * </pre>
     *
     * @param productId The ID of the product to remove.
     * @param userToken The token of the user who is removing the product.
     * @return A {@link ResponseEntity} containing the {@link CartItemModel} of the removed product.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<CartItemResultDTO> removeProductFromCart(@PathVariable long productId, @RequestHeader("Authorization") String userToken) {
        cartItemService.removeProductFromCart(productId, userToken);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes all products from the cart.
     *
     * <p>Example:</p>
     * <pre>
     * DELETE /carts/items
     * Headers: Authorization: Bearer userToken
     * </pre>
     *
     * @param userToken The token of the user who is removing the product.
     * @return A {@link ResponseEntity} containing the {@link CartItemModel} of the removed product.
     */
    @DeleteMapping
    public ResponseEntity<CartItemResultDTO> deleteAllProductsFromCart(@RequestHeader("Authorization") String userToken) {
        cartItemService.deleteAllProductsFromCart(userToken);
        return ResponseEntity.noContent().build();
    }

}