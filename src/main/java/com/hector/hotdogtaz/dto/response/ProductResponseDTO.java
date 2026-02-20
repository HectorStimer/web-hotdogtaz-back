package com.hector.hotdogtaz.dto.response;


import com.hector.hotdogtaz.model.Category;
import com.hector.hotdogtaz.model.Product;
import com.hector.hotdogtaz.model.ProductIngredient;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDTO(Long id, String name, String description, BigDecimal price,
                                 boolean active, String imageUrl, Category category,
                                 List<ProductIngredient> ingredients) {
}
