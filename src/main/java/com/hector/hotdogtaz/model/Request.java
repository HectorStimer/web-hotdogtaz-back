package com.hector.hotdogtaz.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String clientName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.CREATED;


    @Column(nullable = false)
    private BigDecimal total;


    private LocalDate createdAt;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemRequest> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="user_id")
    private User createdBy;


    @ManyToOne
    @JoinColumn(name="command_id")
    private Command command;

    protected Request(){}

    public Request(String clientName, Status status, BigDecimal total){
        this.clientName=clientName;
        this.status= status;
        this.total = total;
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now(); // metodo p qnd criar o usuario tacar a data insta
    }


    public enum Status{
        CREATED,
        IN_PREPARATION,
        READY,
        COMPLETED,
        CANCELED
    }




    public List<ItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemRequest> items) {
        this.items = items;
    }


    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Status getStatus(){
        return status;

    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }




}


