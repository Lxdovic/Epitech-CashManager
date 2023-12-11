package com.moulamanager.api.controllers;

import com.moulamanager.api.services.jwt.JwtUtils;
import com.moulamanager.api.services.stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class StripeController {

    private final StripeService stripeService;
    private final JwtUtils jwtUtils;

    public StripeController(StripeService stripeService, JwtUtils jwtUtils) {
        this.stripeService = stripeService;
        this.jwtUtils = jwtUtils;
    }

    private static final String STRIPE_EXCEPTION = "StripeException: %s";
    private static final String FAILED_TO_CREATE_PAYMENT_INTENT = "Failed to create payment intent: %s";

    @PostMapping("/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestHeader("Authorization") String userToken) {
        try {
            long userId = jwtUtils.getUserIdFromJwtToken(userToken);
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(userId, "eur");
            return ResponseEntity.ok(paymentIntent.getId());
        } catch (StripeException e) {
            System.err.printf(String.format(STRIPE_EXCEPTION, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format(FAILED_TO_CREATE_PAYMENT_INTENT, e.getMessage()));
        }
    }
}