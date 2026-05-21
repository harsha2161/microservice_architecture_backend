package com.example.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Product {

    @Id
    private int id;
    private String name;
    private int price;
    private String description;
    private String image;
    private Boolean isAvailable;
    private int quantity;

}
