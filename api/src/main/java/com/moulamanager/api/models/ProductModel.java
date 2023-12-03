package com.moulamanager.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name can't be empty")
    @Length(max = 255, message = "Name can't be longer than 255 characters")
    private String name;

    @Min(value = 0, message = "Price can't be negative")
    @NotNull(message = "Price can't be null")
    private Double price;

    @Length(max = 255, message = "Description can't be longer than 255 characters")
    private String description;

    @Column(unique = true, length = 13)
    @Size(min = 13, max = 13, message = "Barcode must be 13 characters long")
    @Pattern(regexp = "[0-9]+", message = "Barcode must contain only numbers")
    private String barcode;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemModel> cartItems;
}
