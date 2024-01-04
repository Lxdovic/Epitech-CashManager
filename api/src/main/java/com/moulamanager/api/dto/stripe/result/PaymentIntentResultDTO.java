package com.moulamanager.api.dto.stripe.result;

import com.stripe.model.PaymentIntent;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentIntentResultDTO {
    private String paymentIntentId;
    private String clientSecret;
    private String ephemeralKey;
    private String clientId;

    public static PaymentIntentResultDTO fromPaymentIntent(PaymentIntent paymentIntent, String ephemeralKey) {
        return PaymentIntentResultDTO.builder()
                .paymentIntentId(paymentIntent.getId())
                .clientSecret(paymentIntent.getClientSecret())
                .ephemeralKey(ephemeralKey)
                .clientId(paymentIntent.getCustomer())
                .build();
    }
}