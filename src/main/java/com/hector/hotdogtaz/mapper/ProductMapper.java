package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.response.ProductResponseDTO;
import com.hector.hotdogtaz.model.Product;
import org.springframework.stereotype.Component;
@Component
public class ProductMapper {

    public ProductResponseDTO toResponse(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getActive(),
                product.getImageUrl(),
                product.getCategory(),
                product.getIngredients()
        );
    }
}