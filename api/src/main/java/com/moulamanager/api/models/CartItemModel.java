package com.moulamanager.api.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item")
public class CartItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cart_id", unique = true)
    @JsonIdentityReference(alwaysAsId = true)
    private CartModel cart;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true)
    @JsonIdentityReference(alwaysAsId = true)
    private ProductModel product;

    private int quantity;
}
