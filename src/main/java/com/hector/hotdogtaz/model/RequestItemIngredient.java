package com.hector.hotdogtaz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="request_item_ingredient")
public class RequestItemIngredient {

    @JoinColumn(name = "id_request_item")
    private Request idRequest;



}
