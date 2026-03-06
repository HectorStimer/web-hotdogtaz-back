package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.response.RequestResponseDTO;
import com.hector.hotdogtaz.model.ItemRequest;
import com.hector.hotdogtaz.model.Request;
import com.hector.hotdogtaz.model.RequestEvent;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {
    private final ItemRequestMapper itemRequestMapper;
    private final RequestEventMapper requestEventMapper;

    public RequestMapper(ItemRequestMapper itemRequestMapper,
                         RequestEventMapper requestEventMapper) {
        this.itemRequestMapper = itemRequestMapper;
        this.requestEventMapper = requestEventMapper;
    }

    public RequestResponseDTO toResponse(Request request) {
        if (request == null) return null;
        return new RequestResponseDTO(
                request.getId(),
                request.getStatus(),
                request.getObservation(),
                request.getOrderDate(),
                request.getItems().stream().map(itemRequestMapper::toResponse).toList(),
                request.getEvents().stream().map(requestEventMapper::toResponse).toList()
        );
    }

}
