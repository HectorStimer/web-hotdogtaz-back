package com.hector.hotdogtaz.dto.request.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryDTO (@NotBlank(message= "Nome é Obrigatório")
                                 @Size(min = 2, max = 20, message = "Nome deve de ter entre 2 a 20 caracteres") String name){
}
