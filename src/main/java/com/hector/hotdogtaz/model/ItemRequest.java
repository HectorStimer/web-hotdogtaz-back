package com.hector.hotdogtaz.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "request_items")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @OneToMany(mappedBy = "requestItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestItemIngredient> ingredients = new ArrayList<>();

    protected ItemRequest() {
    }

    public ItemRequest(Request request, Product product, Integer quantity, BigDecimal unitPrice, String observation) {
        this.request = request;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.observation = observation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<RequestItemIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RequestItemIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
