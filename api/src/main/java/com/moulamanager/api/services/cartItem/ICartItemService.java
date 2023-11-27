package com.moulamanager.api.services.cartItem;

import com.moulamanager.api.dto.cartItem.result.CartItemResultDTO;
import com.moulamanager.api.dto.cartItem.request.UpdateCartItemQuantityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICartItemService {

    Page<CartItemResultDTO> findAll(Pageable pageable);

    CartItemResultDTO findById(long id);

    CartItemResultDTO findByCartId(long cartId);

    CartItemResultDTO findByProductId(long productId);

    CartItemResultDTO findByCartIdAndProductId(long cartId, long productId);

    CartItemResultDTO addProductToCart(long productId, String token);

    CartItemResultDTO addProductToCartWithBarcode(String barcode, String token);

    CartItemResultDTO updateProductQuantity(long productId, UpdateCartItemQuantityDTO quantity, String token);

    void removeProductFromCart(long productId, String token);

    void deleteAllProductsFromCart(String token);

}
