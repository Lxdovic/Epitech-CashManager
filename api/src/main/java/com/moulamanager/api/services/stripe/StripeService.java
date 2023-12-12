package com.moulamanager.api.services.stripe;

import com.moulamanager.api.dto.cart.result.CartResultDTO;
import com.moulamanager.api.dto.user.result.UserResultDTO;
import com.moulamanager.api.services.cart.CartService;
import com.moulamanager.api.services.cartItem.CartItemService;
import com.moulamanager.api.services.user.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    private final CustomerService customerService;
    private final UserService userService;
    private final CartItemService cartItemService;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public StripeService(CustomerService customerService, UserService userService, CartItemService cartItemService) {
        this.customerService = customerService;
        this.userService = userService;
        this.cartItemService = cartItemService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public PaymentIntent createPaymentIntent(long userId, String currency) throws StripeException {
        UserResultDTO user = UserResultDTO.fromUserModel(userService.findById(userId));
        double calculatedAmount = calculateOrderAmount(userId);
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) calculatedAmount * 100)
                        .setCurrency(currency)
                        .setCustomer(customerService.findOrCreateCustomer(user.getEmail(), user.getUsername()).getId())
                        .build();

        return PaymentIntent.create(params);
    }

    private double calculateOrderAmount(long userId) {
        return cartItemService.calculateCartTotalPrice(userId);
    }

}