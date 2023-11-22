package com.moulamanager.api.dto;

import com.moulamanager.api.models.CartModel;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CartCreationResultDTO {
    private long id;
    private long userId;
    private Date createdAt;
    private boolean checkedOut;

    public static CartCreationResultDTO fromCartModel(CartModel cart) {
        return CartCreationResultDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .createdAt(cart.getCreatedAt())
                .checkedOut(cart.isCheckedOut())
                .build();
    }
}
