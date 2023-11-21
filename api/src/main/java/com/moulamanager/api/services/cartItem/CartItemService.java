package com.moulamanager.api.services.cartItem;

import com.moulamanager.api.exceptions.cart.CartNotFoundException;
import com.moulamanager.api.exceptions.cartItem.CartItemNotFoundException;
import com.moulamanager.api.exceptions.product.ProductNotFoundException;
import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.repositories.CartItemRepository;
import com.moulamanager.api.repositories.CartRepository;
import com.moulamanager.api.repositories.ProductRepository;
import com.moulamanager.api.services.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService extends AbstractService<CartItemModel> implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    private static final String CART_ITEM_NOT_FOUND = "Cart item not found";

    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<CartItemModel> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItemModel findById(long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND));
    }

    @Override
    public CartItemModel findByCartId(long cartId) {
        return cartItemRepository.findByCartId(cartId).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND));
    }

    @Override
    public CartItemModel findByProductId(long productId) {
        return cartItemRepository.findByProductId(productId).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND));
    }

    @Override
    public CartItemModel save(CartItemModel cartItem) {
        productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + cartItem.getProduct().getId() + " not found"));
        cartRepository.findById(cartItem.getCart().getId())
                .orElseThrow(() -> new CartNotFoundException("Cart with id " + cartItem.getCart().getId() + " not found"));
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItemModel update(CartItemModel cartItem) {
        CartItemModel cartItemModel = cartItemRepository.findById(cartItem.getId())
                .orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND));
        BeanUtils.copyProperties(cartItem, cartItemModel, getNullPropertyNames(cartItem));
        productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + cartItem.getProduct().getId() + " not found"));
        cartRepository.findById(cartItem.getCart().getId())
                .orElseThrow(() -> new CartNotFoundException("Cart with id " + cartItem.getCart().getId() + " not found"));
        return cartItemRepository.save(cartItemModel);
    }

    @Override
    public void delete(long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new CartItemNotFoundException(CART_ITEM_NOT_FOUND);
        }
        cartItemRepository.deleteById(id);
    }
}
