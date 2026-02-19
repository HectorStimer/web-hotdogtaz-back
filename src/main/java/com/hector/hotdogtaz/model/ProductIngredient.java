package com.hector.hotdogtaz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_ingredient")
public class ProductIngredient {
    @JoinColumn(name = "productId")
    private Product product;

    @JoinColumn(name= "ingredientId")
    private Ingredient ingredient;

}
