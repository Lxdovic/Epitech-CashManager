package com.moulamanager.api.dto.stripe.result;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentIntentResultDTO {
    private String paymentIntentId;

    public static PaymentIntentResultDTO fromPaymentIntent(String paymentIntentId) {
        return PaymentIntentResultDTO.builder()
                .paymentIntentId(paymentIntentId)
                .build();
    }
}
