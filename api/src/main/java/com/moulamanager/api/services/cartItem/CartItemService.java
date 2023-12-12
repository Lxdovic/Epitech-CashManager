package com.moulamanager.api.services.cartItem;

import com.moulamanager.api.dto.cart.result.CartResultDTO;
import com.moulamanager.api.dto.cartItem.result.CartItemResultDTO;
import com.moulamanager.api.dto.cartItem.request.UpdateCartItemQuantityDTO;
import com.moulamanager.api.exceptions.cart.CartNotFoundException;
import com.moulamanager.api.exceptions.cartItem.CartItemAlreadyExistsException;
import com.moulamanager.api.exceptions.cartItem.CartItemNotFoundException;
import com.moulamanager.api.exceptions.cartItem.InvalidQuantityException;
import com.moulamanager.api.models.CartItemModel;
import com.moulamanager.api.models.ProductModel;
import com.moulamanager.api.repositories.CartItemRepository;
import com.moulamanager.api.services.AbstractService;
import com.moulamanager.api.services.cart.CartService;
import com.moulamanager.api.services.product.ProductService;
import com.moulamanager.api.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CartItemService extends AbstractService<CartItemModel> implements ICartItemService {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    private static final String CART_ITEM_NOT_FOUND = "Cart item not found";
    private static final String QUANTITY_IS_SAME_AS_PREVIOUS = "Quantity is same as previous quantity";
    private static final String PRODUCT_ALREADY_EXISTS_IN_CART = "Product with id %d already exists in cart with id %d";


    @Override
    public Page<CartItemResultDTO> findAll(Pageable pageable) {
        return CartItemResultDTO.fromCartItemModelList(cartItemRepository.findAll(pageable));
    }

    /**
     * Gets all cart items by user ID.
     *
     * @param pageable The {@link Pageable} to use.
     * @param userId   The id of the user to get the cart items for.
     * @return A {@link Page} of {@link CartItemModel}s.
     * @throws CartNotFoundException if the cart is not found.
     */
    public Page<CartItemResultDTO> findAllByUser(Pageable pageable, long userId) {
        findUserById(userId);
        CartResultDTO cart = findCartByUserIdAndNotCheckedOut(userId);
        return CartItemResultDTO.fromCartItemModelList(cartItemRepository.findAllByCartId(cart.getId(), pageable));
    }

    /**
     * Find a cart item by ID
     *
     * @param id The ID of the cart item to find
     * @return The cart item
     * @throws CartItemNotFoundException If the cart item doesn't exist
     */
    @Override
    public CartItemResultDTO findById(long id) {
        return mapToCartItemResultDTO(cartItemRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND)));
    }

    /**
     * Find a cart item by cart ID and product ID
     *
     * @param cartId    The ID of the cart to find
     * @param productId The ID of the product to find
     * @return The cart item
     * @throws CartItemNotFoundException If the cart item doesn't exist
     */
    @Override
    public CartItemResultDTO findByCartIdAndProductId(long cartId, long productId) {
        return mapToCartItemResultDTO(cartItemRepository.findByCartIdAndProductId(cartId, productId).orElseThrow(() -> new CartItemNotFoundException(CART_ITEM_NOT_FOUND)));
    }

    /**
     * Add a product with the given id to the cart of the user with the given token
     *
     * @param productId The id of the product to add
     * @param userId    The id of the user
     * @return The created cart item
     */
    @Transactional
    @Override
    public CartItemResultDTO addProductToCart(long productId, long userId) {
        ProductModel product = findProductById(productId);
        return addProductToCart(product, userId);
    }

    /**
     * Add a product with the given barcode to the cart of the user with the given token
     *
     * @param barcode The barcode of the product to add
     * @param userId  The id of the user
     * @return The created cart item
     */
    public CartItemResultDTO addProductToCartWithBarcode(String barcode, long userId) {
        ProductModel product = productService.findByBarcode(barcode);
        return addProductToCart(product, userId);
    }

    /**
     * Update the quantity of the cart item with the given product id
     *
     * @param productId The id of the product to update
     * @param quantity  The new quantity
     * @param userId    The id of the user
     * @return The updated cart item
     * @throws CartItemNotFoundException If the cart item doesn't exist
     * @throws IllegalArgumentException  If the quantity is less than or equal to 0
     * @throws IllegalArgumentException  If the quantity is the same as the previous quantity
     */
    @Override
    public CartItemResultDTO updateProductQuantity(long productId, UpdateCartItemQuantityDTO quantity, long userId) {

        findUserById(userId);

        CartResultDTO cart = findCartByUserIdAndNotCheckedOut(userId);
        CartItemResultDTO cartItem = findByCartIdAndProductId(cart.getId(), productId);

        checkIfSameQuantity(quantity.getQuantity(), cartItem);

        updateCartItemQuantity(cartItem, quantity.getQuantity());

        return cartItem;
    }

    /**
     * Remove the product with the given id from the cart of the user with the given token
     *
     * @param productId The id of the product to remove
     * @param userId    The id of the user
     * @throws CartItemNotFoundException If the cart item doesn't exist
     */
    @Override
    public void deleteProductFromCart(long productId, long userId) {
        CartResultDTO cart = getCartForUser(userId);
        CartItemResultDTO cartItem = findByCartIdAndProductId(cart.getId(), productId);
        cartItemRepository.delete(mapToCartItemModel(cartItem));
    }

    /**
     * Remove all products from the cart of the user with the given token
     *
     * @param userId The id of the user
     * @throws CartItemNotFoundException If the cart item doesn't exist
     */
    @Transactional
    @Override
    public void deleteAllProductsFromCart(long userId) {
        CartResultDTO cart = getCartForUser(userId);
        if (!cartItemRepository.existsByCartId(cart.getId())) {
            throw new RuntimeException("Cart is empty");
        }
        cartItemRepository.deleteAllByCartId(cart.getId());
    }

    /**
     * Calculate the total price of the cart of the user
     *
     * @param userId The id of the user
     * @return The total price of the cart
     */
    public double calculateCartTotalPrice(long userId) {
        CartResultDTO cart = getCartForUser(userId);
        List<CartItemModel> cartItems = cartItemRepository.findAllByCartId(cart.getId(), Pageable.unpaged()).getContent();
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Add a product to the cart of the user with the given token
     * If the user doesn't have a cart, create a new one
     * If the product already exists in the cart, throw an exception
     *
     * @param product The product to add to the cart
     * @param userId  The id of the user
     * @return The created cart item
     * @throws CartItemAlreadyExistsException If the product already exists in the cart
     */
    private CartItemResultDTO addProductToCart(ProductModel product, long userId) {
        CartResultDTO cart = getOrCreateCartForUser(userId);
        checkProductExistsInCart(cart, product);
        CartItemModel cartItem = getOrCreateCartItemForProductInCart(product, cart);
        return mapToCartItemResultDTO(cartItem);
    }

    private void checkIfSameQuantity(int quantity, CartItemResultDTO cartItem) {
        if (cartItem.getQuantity() == quantity) {
            throw new InvalidQuantityException(QUANTITY_IS_SAME_AS_PREVIOUS);
        }
    }

    // Find a way to use this method on every service without having to copy and paste it nor having to create an interface/abstract class, so we don't need to inject the JwtUtils on every service
    /*private long getUserIdFromToken(String token) {
        return jwtUtils.getUserIdFromJwtToken(token);
    }*/

    private void findUserById(long userId) {
        userService.findById(userId);
    }

    private CartResultDTO getCartForUser(long userId) {
        findUserById(userId);
        return findCartByUserIdAndNotCheckedOut(userId);
    }

    private ProductModel findProductById(long productId) {
        return productService.findById(productId);
    }

    private CartResultDTO findCartByUserIdAndNotCheckedOut(long userId) {
        return cartService.findByUserIdAndCheckedOut(userId, false);
    }

    private CartResultDTO getOrCreateCartForUser(long userId) {
        try {
            return findCartByUserIdAndNotCheckedOut(userId);
        } catch (CartNotFoundException e) {
            return createNewCart(userId);
        }
    }

    private void checkProductExistsInCart(CartResultDTO cart, ProductModel product) {
        if (cartItemRepository.existsByCartIdAndProductId(cart.getId(), product.getId())) {
            throw new CartItemAlreadyExistsException(String.format(PRODUCT_ALREADY_EXISTS_IN_CART, product.getId(), cart.getId()));
        }
    }

    private double calculateTotalPrice(CartItemResultDTO cartItem) {
        return cartItem.getProduct().getPrice() * cartItem.getQuantity();
    }

    private CartResultDTO createNewCart(long userId) {
        return cartService.save(userId);
    }

    private CartItemModel getOrCreateCartItemForProductInCart(ProductModel product, CartResultDTO cart) {
        return cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElseGet(() -> createNewCartItem(product, cart));
    }

    private CartItemModel createNewCartItem(ProductModel product, CartResultDTO cart) {
        CartItemModel newCartItem = new CartItemModel();
        newCartItem.setProduct(product);
        newCartItem.setCart(CartResultDTO.toCartModel(cart));
        return cartItemRepository.save(newCartItem);
    }

    private void updateCartItemQuantity(CartItemResultDTO cartItem, int quantity) {
        cartItem.setQuantity(quantity);
        cartItemRepository.save(mapToCartItemModel(cartItem));
    }

    private CartItemResultDTO mapToCartItemResultDTO(CartItemModel cartItem) {
        return CartItemResultDTO.fromCartItemModel(cartItem);
    }

    private CartItemModel mapToCartItemModel(CartItemResultDTO cartItem) {
        return CartItemResultDTO.toCartItemModel(cartItem);
    }
}