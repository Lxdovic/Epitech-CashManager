package com.moulamanager.api.services.cartItem;

import com.moulamanager.api.dto.cartItem.result.CartItemResultDTO;
import com.moulamanager.api.dto.cartItem.request.UpdateCartItemQuantityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICartItemService {

    // Find methods
    Page<CartItemResultDTO> findAll(Pageable pageable);
    Page<CartItemResultDTO> findAllByUser(Pageable pageable, long userId);
    CartItemResultDTO findById(long id);
    CartItemResultDTO findByCartIdAndProductId(long cartId, long productId);

    // Add methods
    CartItemResultDTO addProductToCart(long productId, long userId);
    CartItemResultDTO addProductToCartWithBarcode(String barcode, long userId);

    // Update methods
    CartItemResultDTO updateProductQuantity(long productId, UpdateCartItemQuantityDTO quantity, long userId);

    // Delete methods
    void deleteProductFromCart(long productId, long userId);
    void deleteAllProductsFromCart(long userId);

}
