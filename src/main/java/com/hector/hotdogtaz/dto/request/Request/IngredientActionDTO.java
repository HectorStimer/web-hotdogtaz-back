package com.hector.hotdogtaz.dto.request.Request;

import com.hector.hotdogtaz.model.RequestItemIngredient;

public record IngredientActionDTO(
        Long ingredientId,
        RequestItemIngredient.Action action
) {}
