package com.moulamanager.api.services.cart;

import com.moulamanager.api.dto.CartResultDTO;
import com.moulamanager.api.exceptions.cart.CartAlreadyExistsException;
import com.moulamanager.api.exceptions.cart.CartNotFoundException;
import com.moulamanager.api.exceptions.user.UserNotFoundException;
import com.moulamanager.api.models.CartModel;
import com.moulamanager.api.models.UserModel;
import com.moulamanager.api.repositories.CartRepository;
import com.moulamanager.api.repositories.UserRepository;
import com.moulamanager.api.services.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public List<CartResultDTO> findAll() {
        return CartResultDTO.fromCartModelList(cartRepository.findAll());
    }

    @Override
    public CartResultDTO findById(long id) {
        return CartResultDTO.fromCartModel(cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND)));
    }

    @Override
    public CartResultDTO findByUserId(long userId) {
        return CartResultDTO.fromCartModel(cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND)));
    }

    @Override
    public CartResultDTO findByUserIdAndCheckedOut(long userId, boolean checkedOut) {
        return CartResultDTO.fromCartModel(cartRepository.findByUserIdAndCheckedOut(userId, checkedOut).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND)));
    }

    @Override
    public CartResultDTO save(long userId) {
        UserModel user = findUserById(userId);
        validateCartExistence(userId);

        CartModel newCart = createNewCart(user);
        return CartResultDTO.fromCartModel(cartRepository.save(newCart));
    }

    @Override
    public CartResultDTO update(CartModel cart) {
        CartModel cartModel = cartRepository.findById(cart.getId()).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND));
        BeanUtils.copyProperties(cart, cartModel, getNullPropertyNames(cart));
        if (cartRepository.existsByUserId(cart.getUser().getId())) {
            throw new CartAlreadyExistsException("User with id " + cart.getUser().getId() + " already has an active cart");
        }

        return CartResultDTO.fromCartModel(cartRepository.save(cartModel));
    }

    @Override
    public void delete(long id) {
        if (!cartRepository.existsById(id)) {
            throw new CartNotFoundException(CART_NOT_FOUND);
        }
        cartRepository.deleteById(id);
    }

    private UserModel findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
    }

    private void validateCartExistence(long userId) {
        cartRepository.findByUserIdAndCheckedOut(userId, false)
                .filter(c -> !c.isCheckedOut())
                .ifPresent(c -> {
                    throw new CartAlreadyExistsException("User with id " + userId + " already has an active cart");
                });
    }

    private CartModel createNewCart(UserModel user) {
        CartModel newCart = new CartModel();
        newCart.setUser(user);
        newCart.setCheckedOut(false);
        newCart.setCreatedAt(new Date());
        return newCart;
    }
}
