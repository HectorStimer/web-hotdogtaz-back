package com.hector.hotdogtaz.dto.request.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record UpdateProductDTO (@NotBlank(message = "O nome do produto nao pode estar em branco")
                                String name,

                                @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
                                String description,


                                @NotNull(message = "Preço do produto é obrigatório")
                                @Positive(message = "Preço deve ser maior que zero")
                                BigDecimal price,

                                boolean active, String imageUrl,

                                @NotNull(message = "Categoria é obrigatória")
                                Long categoryId,

                                List<Long> ingredientId){
}
