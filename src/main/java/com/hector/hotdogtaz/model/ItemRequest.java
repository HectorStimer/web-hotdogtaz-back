package com.hector.hotdogtaz.model;


import jakarta.persistence.*;

@Entity
@Table(name= "item_request")
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
    private int amount;


    protected ItemRequest() {
    }

    public ItemRequest(Request request, Product product, int amount) {
        this.request = request;
        this.product = product;
        this.amount = amount;
    }


    public Long getId() {
        return id;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
