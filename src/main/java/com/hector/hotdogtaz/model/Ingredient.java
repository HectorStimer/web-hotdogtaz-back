package com.hector.hotdogtaz.model;


import jakarta.persistence.*;

@Entity
@Table(name= "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;

}
