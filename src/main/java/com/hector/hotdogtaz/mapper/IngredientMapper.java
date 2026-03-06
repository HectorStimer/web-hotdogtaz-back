package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.response.IngredientResponseDTO;
import com.hector.hotdogtaz.model.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {
    public IngredientResponseDTO toResponse(Ingredient ingredient) {
        if (ingredient == null) return null;
        return new IngredientResponseDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getActive()
        );
    }
}