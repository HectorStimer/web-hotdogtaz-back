package com.hector.hotdogtaz.model;
import java.math.BigDecimal;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="category_id", nullable= false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductIngredient> ingredients = new ArrayList<>();

    protected Product(){}

    public Product(String name, String description, BigDecimal price, Category category, Boolean active, String imageUrl){
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.active = active != null ? active : true;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<ProductIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
