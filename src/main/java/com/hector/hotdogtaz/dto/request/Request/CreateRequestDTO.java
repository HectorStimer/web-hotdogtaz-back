package com.hector.hotdogtaz.dto.request.Request;

import com.hector.hotdogtaz.model.Command;
import com.hector.hotdogtaz.model.ItemRequest;
import com.hector.hotdogtaz.model.RequestEvent;
import com.hector.hotdogtaz.model.RequestItemIngredient;

import java.util.List;

public record CreateRequestDTO(
        String observation,
        Long commandId,
        List<CreateItemRequestDTO> items

) {}


