package com.hector.hotdogtaz.model;

import jakarta.persistence.*;

@Entity
@Table(name = "request_item_ingredients")
public class RequestItemIngredient {

    @EmbeddedId
    private RequestItemIngredientId id;

    @ManyToOne
    @MapsId("requestItemId")
    @JoinColumn(name = "request_item_id", nullable = false)
    private ItemRequest requestItem;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    protected RequestItemIngredient() {}

    public RequestItemIngredient(ItemRequest requestItem, Ingredient ingredient, Action action) {
        this.requestItem = requestItem;
        this.ingredient = ingredient;
        this.action = action;
        this.id = new RequestItemIngredientId(requestItem.getId(), ingredient.getId());
    }

    public enum Action {
        ADD,
        REMOVE,
        REPLACE
    }

    public RequestItemIngredientId getId() {
        return id;
    }

    public void setId(RequestItemIngredientId id) {
        this.id = id;
    }

    public ItemRequest getRequestItem() {
        return requestItem;
    }

    public void setRequestItem(ItemRequest requestItem) {
        this.requestItem = requestItem;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
