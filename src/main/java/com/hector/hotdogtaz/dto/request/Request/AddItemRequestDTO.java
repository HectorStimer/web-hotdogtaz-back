package com.hector.hotdogtaz.dto.request.Request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record AddItemRequestDTO(
        Long productId,
        @NotNull(message = "Quantidade do produto é obrigatório")
        @Positive(message = "Quantidade deve ser maior que zero")
        Integer quantity,
        String observation,
        List<IngredientActionDTO> ingredients
) {}