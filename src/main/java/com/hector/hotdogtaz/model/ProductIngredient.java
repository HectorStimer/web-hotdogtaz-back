package com.hector.hotdogtaz.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_ingredient")
public class ProductIngredient {

    @EmbeddedId
    private ProductIngredientId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    protected ProductIngredient() {}

    public ProductIngredient(Product product, Ingredient ingredient) {
        this.product = product;
        this.ingredient = ingredient;
        this.id = new ProductIngredientId(product.getId(), ingredient.getId());
    }

    public ProductIngredientId getId() {
        return id;
    }

    public void setId(ProductIngredientId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
