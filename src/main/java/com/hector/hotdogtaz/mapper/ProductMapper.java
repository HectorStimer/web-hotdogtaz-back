package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.response.ProductResponseDTO;
import com.hector.hotdogtaz.model.Product;
import org.springframework.stereotype.Component;
@Component
public class ProductMapper {
    private final CategoryMapper categoryMapper;
    private final IngredientMapper ingredientMapper;

    public ProductMapper(CategoryMapper categoryMapper, IngredientMapper ingredientMapper) {
        this.categoryMapper = categoryMapper;
        this.ingredientMapper = ingredientMapper;
    }

    public ProductResponseDTO toResponse(Product product) {
        if (product == null) return null;
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getActive(),
                product.getImageUrl(),
                categoryMapper.toResponse(product.getCategory()),
                product.getIngredients().stream()
                        .map(pi -> ingredientMapper.toResponse(pi.getIngredient()))
                        .toList()
        );
    }
}