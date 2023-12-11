package com.moulamanager.api.controllers;

import com.moulamanager.api.dto.stripe.result.PaymentIntentResultDTO;
import com.moulamanager.api.services.jwt.JwtUtils;
import com.moulamanager.api.services.stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class StripeController {

    private final StripeService stripeService;
    private final JwtUtils jwtUtils;

    private static final String STRIPE_EXCEPTION = "StripeException: %s";

    public StripeController(StripeService stripeService, JwtUtils jwtUtils) {
        this.stripeService = stripeService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentIntentResultDTO> createPaymentIntent(@RequestHeader("Authorization") String userToken) {
        try {
            long userId = jwtUtils.getUserIdFromJwtToken(userToken);
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(userId, "eur");
            return ResponseEntity.ok(PaymentIntentResultDTO.fromPaymentIntent(paymentIntent));
        } catch (StripeException e) {
            System.err.printf(String.format(STRIPE_EXCEPTION, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}