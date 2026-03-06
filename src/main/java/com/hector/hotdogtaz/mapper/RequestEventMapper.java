package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.response.RequestEventResponseDTO;
import com.hector.hotdogtaz.model.RequestEvent;
import org.springframework.stereotype.Component;

@Component
public class RequestEventMapper {
    public RequestEventResponseDTO toResponse(RequestEvent event){
        if(event == null) return null;
        return new RequestEventResponseDTO(
                event.getId(),
                event.getPreviousStatus(),
                event.getNewStatus(),
                event.getEventDate()
        );

    }}
