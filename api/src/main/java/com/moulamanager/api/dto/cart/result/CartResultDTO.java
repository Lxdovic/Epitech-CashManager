package com.moulamanager.api.dto.cart.result;

import com.moulamanager.api.models.CartModel;
import com.moulamanager.api.models.UserModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Date;

@Data
@Builder
public class CartResultDTO {
    private long id;
    private long userId;
    private Date createdAt;
    private boolean checkedOut;
    private double totalPrice;

    public static CartResultDTO fromCartModel(CartModel cart) {
        return CartResultDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .createdAt(cart.getCreatedAt())
                .checkedOut(cart.isCheckedOut())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    public static Page<CartResultDTO> fromCartModelList(Page<CartModel> carts) {
        return carts.map(CartResultDTO::fromCartModel);
    }

    public static CartModel toCartModel(CartResultDTO cartResultDTO) {
        return CartModel.builder()
                .id(cartResultDTO.getId())
                .user(UserModel.builder().id(cartResultDTO.getUserId()).build())
                .createdAt(cartResultDTO.getCreatedAt())
                .checkedOut(cartResultDTO.isCheckedOut())
                .totalPrice(cartResultDTO.getTotalPrice())
                .build();
    }
}
