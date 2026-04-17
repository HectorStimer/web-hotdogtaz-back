package com.hector.hotdogtaz.dto.request.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateItemRequestDTO(
        @NotNull(message = "Quantidade do produto é obrigatório")
        @Positive(message = "Quantidade deve ser maior que zero")  Integer quantity,
        String observation
) {}