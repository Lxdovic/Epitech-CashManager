package com.moulamanager.api.dto.stripe.result;

import com.stripe.model.PaymentIntent;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentIntentResultDTO {
    private String paymentIntentId;

    public static PaymentIntentResultDTO fromPaymentIntent(PaymentIntent paymentIntent) {
        return PaymentIntentResultDTO.builder()
                .paymentIntentId(paymentIntent.getId())
                .build();
    }
}
