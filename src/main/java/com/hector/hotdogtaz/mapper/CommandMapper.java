package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.response.CommandResponseDTO;
import com.hector.hotdogtaz.dto.response.RequestSummaryDTO;
import com.hector.hotdogtaz.model.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandMapper {
    private final RequestMapper requestMapper;

    public CommandMapper(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    public CommandResponseDTO toResponse(Command command) {
        if (command == null) return null;
        return new CommandResponseDTO(
                command.getId(),
                command.getNumber(),
                command.getTableNumber(),
                command.getStatus(),
                command.getTotal(),
                command.getObservation(),
                command.getOpeningDate(),
                command.getClosingDate(),
                command.getRequests().stream()
                        .map(r -> new RequestSummaryDTO(r.getId(), r.getStatus(), r.getOrderDate()))
                        .toList()
        );
    }
}