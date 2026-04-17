package com.hector.hotdogtaz.dto.request.Command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCommandDTO(
        @NotNull(message = "Número da comanda é obrigatório")
        @Positive(message = "Número da comanda deve ser maior que zero")
        Integer number,

        @NotNull(message = "Número da mesa é obrigatório")
        @Positive(message = "Número da mesa deve ser maior que zero")
        Integer tableNumber,

        String observation
) {}