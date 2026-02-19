package com.hector.hotdogtaz.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RequestItemIngredientId implements Serializable {

    private Long requestItemId;
    private Long ingredientId;

    protected RequestItemIngredientId() {}

    public RequestItemIngredientId(Long requestItemId, Long ingredientId) {
        this.requestItemId = requestItemId;
        this.ingredientId = ingredientId;
    }

    public Long getRequestItemId() {
        return requestItemId;
    }

    public void setRequestItemId(Long requestItemId) {
        this.requestItemId = requestItemId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestItemIngredientId that = (RequestItemIngredientId) o;
        return Objects.equals(requestItemId, that.requestItemId) &&
               Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestItemId, ingredientId);
    }
}
