package com.hector.hotdogtaz.dto.request;

import com.hector.hotdogtaz.model.Category;
import com.hector.hotdogtaz.model.ProductIngredient;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductDTO(String name, String description, BigDecimal price,
                               String imageUrl, Category category,
                               List<ProductIngredient> ingredients) {
}
