package com.hector.hotdogtaz.dto.request.Request;

import java.util.List;

public record CreateItemRequestDTO(
        Long productId,
        Integer quantity,
        String observation,
        List<IngredientActionDTO> ingredients
) {}
