package com.hector.hotdogtaz.model;


import jakarta.persistence.*;

@Entity
@Table(name= "item_request")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="request_id", nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;


    @Column(nullable = false)
    private int amount;

    private String observation;





    protected ItemRequest(){}

    public ItemRequest(Request request, Product product, int amount, String observation){
        this.request=request;
        this.product=product;
        this.amount=amount;
        this.observation=observation;
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
