package com.hector.hotdogtaz.dto.request.Request;

import java.util.List;

public record AddItemRequestDTO(
        Long productId,
        Integer quantity,
        String observation,
        List<IngredientActionDTO> ingredients
) {}