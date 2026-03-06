package com.hector.hotdogtaz.mapper;


import com.hector.hotdogtaz.dto.response.CategoryResponseDTO;
import com.hector.hotdogtaz.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponseDTO toResponse(Category category) {
        if (category == null) return null;
        return new CategoryResponseDTO(category.getId(), category.getName());
    }
}