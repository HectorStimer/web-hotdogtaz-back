package com.hector.hotdogtaz.dto.response;

import java.math.BigDecimal;

public record ItemRequestResponseDTO(
        Long id, ProductResponseDTO product,
        Integer quantity, BigDecimal unitPrice, String observation
) {}