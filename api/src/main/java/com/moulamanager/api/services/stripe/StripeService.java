package com.moulamanager.api.services.stripe;

import com.moulamanager.api.dto.cart.result.CartResultDTO;
import com.moulamanager.api.dto.stripe.result.PaymentIntentResultDTO;
import com.moulamanager.api.dto.user.result.UserResultDTO;
import com.moulamanager.api.services.cart.CartService;
import com.moulamanager.api.services.cartItem.CartItemService;
import com.moulamanager.api.services.user.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public PaymentIntentResultDTO createPaymentIntent(long userId, String currency) throws StripeException {
        UserResultDTO user = getUser(userId);
        BigDecimal amountInCents = calculateAmountInCents(userId);
        PaymentIntentCreateParams params = createPaymentIntentParams(user, amountInCents, currency);
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        EphemeralKey ephemeralKey = createEphemeralKey(paymentIntent.getCustomer());
        return PaymentIntentResultDTO.fromPaymentIntent(paymentIntent, ephemeralKey.getSecret());
    }

    private EphemeralKey createEphemeralKey(String customerId) throws StripeException {
        EphemeralKeyCreateParams params = EphemeralKeyCreateParams.builder()
                .setCustomer(customerId)
                .setStripeVersion("2023-10-16")
                .build();

        return EphemeralKey.create(params);
    }

    private UserResultDTO getUser(long userId) {
        return UserResultDTO.fromUserModel(userService.findById(userId));
    }

    private BigDecimal calculateAmountInCents(long userId) {
        BigDecimal calculatedAmount = BigDecimal.valueOf(calculateOrderAmount(userId));
        return calculatedAmount.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP);
    }

    private PaymentIntentCreateParams createPaymentIntentParams(UserResultDTO user, BigDecimal amountInCents, String currency) throws StripeException {
        return PaymentIntentCreateParams.builder()
                .setAmount(amountInCents.longValue())
                .setCurrency(currency)
                .setCustomer(customerService.findOrCreateCustomer(user.getEmail(), user.getUsername()).getId())
                .build();
    }

    private double calculateOrderAmount(long userId) {
        return cartItemService.calculateCartTotalPrice(userId);
    }

}