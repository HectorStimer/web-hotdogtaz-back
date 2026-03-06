package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.response.ItemRequestResponseDTO;
import com.hector.hotdogtaz.model.ItemRequest;
import org.springframework.stereotype.Component;

@Component
public class ItemRequestMapper {
    private final ProductMapper productMapper;

    public ItemRequestMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ItemRequestResponseDTO toResponse(ItemRequest item) {
        if (item == null) return null;
        return new ItemRequestResponseDTO(
                item.getId(),
                productMapper.toResponse(item.getProduct()),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getObservation()
        );
    }
}