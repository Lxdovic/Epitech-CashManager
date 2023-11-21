package com.moulamanager.api.services.cart;

import com.moulamanager.api.exceptions.cart.CartAlreadyExistsException;
import com.moulamanager.api.exceptions.cart.CartNotFoundException;
import com.moulamanager.api.exceptions.user.UserNotFoundException;
import com.moulamanager.api.models.CartModel;
import com.moulamanager.api.repositories.CartRepository;
import com.moulamanager.api.repositories.UserRepository;
import com.moulamanager.api.services.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService extends AbstractService<CartModel> implements ICartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final String CART_NOT_FOUND = "Cart not found";

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CartModel> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public CartModel findById(long id) {
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND));
    }

    @Override
    public CartModel findByUserId(long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND));
    }

    @Override
    public CartModel save(CartModel cart) {
        userRepository.findById(cart.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + cart.getUser().getId() + " not found"));
        if (cartRepository.existsByUserId(cart.getUser().getId())) {
            throw new CartAlreadyExistsException("User with id " + cart.getUser().getId() + " already has a cart");
        }
        return cartRepository.save(cart);
    }

    @Override
    public CartModel update(CartModel cart) {
        System.out.println(cart);
        CartModel cartModel = cartRepository.findById(cart.getId())
                .orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND));
        BeanUtils.copyProperties(cart, cartModel, getNullPropertyNames(cart));
        if (cartRepository.existsByUserId(cart.getUser().getId())) {
            throw new CartAlreadyExistsException("User with id " + cart.getUser().getId() + " already has a cart");
        }

        return cartRepository.save(cartModel);
    }

    @Override
    public void delete(long id) {
        if (!cartRepository.existsById(id)) {
            throw new CartNotFoundException(CART_NOT_FOUND);
        }
        cartRepository.deleteById(id);
    }
}
