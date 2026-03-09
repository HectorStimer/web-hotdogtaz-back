package com.hector.hotdogtaz.model;


import jakarta.persistence.*;

@Entity
@Table(name= "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    protected Ingredient() {}

    public Ingredient(String name) {
        this.name = name;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active=active;

    }}
