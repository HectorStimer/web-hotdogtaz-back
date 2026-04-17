package com.hector.hotdogtaz.dto.request.Ingredient;

import jakarta.validation.constraints.NotBlank;

public record IngredientDTO(@NotBlank(message = "o Ingrediente nao pode estar com o nome em branco") String name) {
}
