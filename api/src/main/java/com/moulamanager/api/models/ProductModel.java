package com.moulamanager.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Min(value = 0, message = "Price can't be negative")
    private Double price;

    private String description;

    @Column(unique = true, length = 13)
    @Size(min = 13, max = 13, message = "Barcode must be 13 characters long")
    private String barcode;
}
