package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.request.Request.CreateRequestDTO;
import com.hector.hotdogtaz.dto.response.RequestResponseDTO;
import com.hector.hotdogtaz.model.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public static RequestResponseDTO toResponse(Request request) {
        if (request == null) {
            return null;
        }
        return new RequestResponseDTO(
                request.getId(),
                request.getStatus(),
                request.getObservation(),
                request.getOrderDate(),
                request.getItems(),
                request.getEvents()
        );
    }

    public static Request fromCreate(CreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        if (dto.command() == null) {
            throw new IllegalArgumentException("Command must be provided when creating a request");
        }
        Request request = new Request(Request.Status.CREATED,
                                      dto.observation(),
                                      dto.command());
        if (dto.items() != null) {
            dto.items().forEach(item -> item.setRequest(request));
            request.setItems(dto.items());
        }
        if (dto.events() != null) {
            dto.events().forEach(ev -> ev.setRequest(request));
            request.setEvents(dto.events());
        }
        return request;
    }
}
