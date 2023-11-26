package com.moulamanager.api.services.cartItem;

import com.moulamanager.api.dto.CartResultDTO;
import com.moulamanager.api.dto.CartItemResultDTO;
import com.moulamanager.api.dto.UpdateCartItemQuantityDTO;
import com.moulamanager.api.exceptions.cart.CartNotFoundException;
import com.moulamanager.api.exceptions.cartItem.CartItemAlreadyExistsException;
import com.moulamanager.api.exceptions.cartItem.CartItemNotFoundException;
import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.models.ProductModel;
import com.moulamanager.api.models.UserModel;
import com.moulamanager.api.repositories.CartItemRepository;
import com.moulamanager.api.services.AbstractService;
import com.moulamanager.api.services.cart.CartService;
import com.moulamanager.api.services.jwt.JwtUtils;
import com.moulamanager.api.services.product.ProductService;
import com.moulamanager.api.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService extends AbstractService<CartItemModel> implements ICartItemService {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;
    private final JwtUtils jwtUtils;

    private static final String CART_ITEM_NOT_FOUND = "Cart item not found";

    public CartItemService(CartItemRepository cartItemRepository, JwtUtils jwtUtils, UserService userService, ProductService productService, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public List<CartItemResultDTO> findAll() {
        return CartItemResultDTO.fromCartItemModelList(cartItemRepository.findAll());
    }

    @Override
    public CartItemResultDTO findById(long id) {
        return createCartItemCreationResultDTO(cartItemRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND)));
    }

    @Override
    public CartItemResultDTO findByCartId(long cartId) {
        return createCartItemCreationResultDTO(cartItemRepository.findByCartId(cartId).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND)));
    }

    @Override
    public CartItemResultDTO findByProductId(long productId) {
        return createCartItemCreationResultDTO(cartItemRepository.findByProductId(productId).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND)));
    }

    @Override
    public CartItemResultDTO findByCartIdAndProductId(long cartId, long productId) {
        return CartItemResultDTO.fromCartItemModel(cartItemRepository.findByCartIdAndProductId(cartId, productId).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND)));
    }

    /**
     * Add a product to the cart of the user with the given token
     * If the user doesn't have a cart, create a new one
     * If the product already exists in the cart, throw an exception
     *
     * @param productId The id of the product to add to the cart
     * @param userToken The token of the user
     * @return The created cart item
     */
    @Override
    public CartItemResultDTO addProductToCart(long productId, String userToken) {
        long userId = jwtUtils.getUserIdFromJwtToken(userToken);
        UserModel user = findUserById(userId);
        ProductModel product = findProductById(productId);
        CartResultDTO cart;
        try {
            cart = findCartByUserIdAndNotCheckedOut(user);
        } catch (CartNotFoundException e) {
            cart = createAndSaveNewCart(user);
        }

        if (cartItemRepository.existsByCartIdAndProductId(cart.getId(), productId)) {
            throw new CartItemAlreadyExistsException("Product with id " + productId + " already exists in cart with id " + cart.getId());
        }

        CartItemModel cartItem = getOrCreateCartItemForProductInCart(product, cart);
        return createCartItemCreationResultDTO(cartItem);
    }

    @Override
    public CartItemResultDTO save(CartItemModel cartItem) {
        findProductById(cartItem.getProduct().getId());
        return createCartItemCreationResultDTO(cartItemRepository.save(cartItem));
    }

    /**
     * Update the quantity of the cart item with the given product id
     * If the cart item doesn't exist, throw an exception
     * If the quantity is less than or equal to 0, throw an exception
     *
     * @param productId The id of the product to update
     * @param quantity  The new quantity
     * @param userToken The token of the user
     * @return The updated cart item
     */
    @Override
    public CartItemResultDTO updateProductQuantity(long productId, UpdateCartItemQuantityDTO quantity, String userToken) {

        validateQuantity(quantity.getQuantity());
        long userId = jwtUtils.getUserIdFromJwtToken(userToken);
        UserModel user = findUserById(userId);
        CartResultDTO cart = findCartByUserIdAndNotCheckedOut(user);
        CartItemResultDTO cartItem = findByCartIdAndProductId(cart.getId(), productId);
        checkIfSameQuantity(quantity.getQuantity(), cartItem);
        updateCartItemQuantity(cartItem, quantity.getQuantity());
        return cartItem;
    }

    @Override
    public void delete(long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new CartItemNotFoundException(CART_ITEM_NOT_FOUND);
        }
        cartItemRepository.deleteById(id);
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }

    private void checkIfSameQuantity(int quantity, CartItemResultDTO cartItem) {
        if (cartItem.getQuantity() == quantity) {
            throw new IllegalArgumentException("Quantity is same as previous quantity");
        }
    }

    // Find a way to use this method on every service without having to copy and paste it nor having to create an interface/abstract class, so we don't need to inject the JwtUtils on every service
    /*private long getUserIdFromToken(String token) {
        return jwtUtils.getUserIdFromJwtToken(token);
    }*/

    private UserModel findUserById(long userId) {
        return userService.findById(userId);
    }

    private ProductModel findProductById(long productId) {
        return productService.findById(productId);
    }

    private CartResultDTO findCartByUserIdAndNotCheckedOut(UserModel user) {
        return cartService.findByUserIdAndCheckedOut(user.getId(), false);
    }

    private CartResultDTO createAndSaveNewCart(UserModel user) {
        CartResultDTO cartCreationResultDTO = cartService.save(user.getId());
        return cartService.findById(cartCreationResultDTO.getId());
    }

    private CartItemModel getOrCreateCartItemForProductInCart(ProductModel product, CartResultDTO cart) {
        return cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElseGet(() -> createNewCartItem(product, cart));
    }

    private CartItemModel createNewCartItem(ProductModel product, CartResultDTO cart) {
        CartItemModel newCartItem = new CartItemModel();
        newCartItem.setProduct(product);
        newCartItem.setCart(CartResultDTO.toCartModel(cart));
        newCartItem.setQuantity(1);
        return cartItemRepository.save(newCartItem);
    }

    private void updateCartItemQuantity(CartItemResultDTO cartItem, int quantity) {
        cartItem.setQuantity(quantity);
        cartItemRepository.save(CartItemResultDTO.toCartItemModel(cartItem));
    }

    private CartItemResultDTO createCartItemCreationResultDTO(CartItemModel cartItem) {
        return CartItemResultDTO.fromCartItemModel(cartItem);
    }
}