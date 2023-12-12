package com.moulamanager.api.services.cart;

import com.moulamanager.api.dto.cart.result.CartResultDTO;
import com.moulamanager.api.exceptions.cart.CartAlreadyExistsException;
import com.moulamanager.api.exceptions.cart.CartNotFoundException;
import com.moulamanager.api.exceptions.user.UserNotFoundException;
import com.moulamanager.api.models.CartModel;
import com.moulamanager.api.models.UserModel;
import com.moulamanager.api.repositories.CartRepository;
import com.moulamanager.api.repositories.UserRepository;
import com.moulamanager.api.services.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService extends AbstractService<CartModel> implements ICartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final String CART_NOT_FOUND = "Cart not found";

    @Override
    public Page<CartResultDTO> findAll(Pageable pageable) {
        return CartResultDTO.fromCartModelList(cartRepository.findAll(pageable));
    }

    @Override
    public CartResultDTO findById(long id) {
        return CartResultDTO.fromCartModel(cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND)));
    }

    @Override
    public CartResultDTO findByUserId(long userId) {
        return CartResultDTO.fromCartModel(cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND)));
    }

    /**
     * Gets a cart by user ID and checked out status.
     * @param userId The ID of the user to get the cart for.
     * @param checkedOut The checked out status of the cart to get.
     * @return A {@link CartModel} of the cart.
     * @throws CartNotFoundException if the cart is not found.
     */
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
        cartRepository.findByUserIdAndCheckedOut(userId, false).ifPresent(cart -> {
            throw new CartAlreadyExistsException("User with id " + userId + " already has an active cart");
        });
    }

    private CartModel createNewCart(UserModel user) {
        CartModel newCart = new CartModel();
        newCart.setUser(user);
        return newCart;
    }
}
