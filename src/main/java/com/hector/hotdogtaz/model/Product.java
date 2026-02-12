package com.hector.hotdogtaz.model;
import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name= "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;   // fiz com big decimal, pq float e double é bugado


    @ManyToOne
    @JoinColumn(name="category_id", nullable= false)
    private Category category;

    protected Product(){}

    public Product(String name, BigDecimal price,Category category){
        this.name=name;
        this.price=price;
        this.category=category;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
