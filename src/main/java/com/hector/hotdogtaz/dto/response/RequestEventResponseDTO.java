package com.hector.hotdogtaz.dto.response;

import com.hector.hotdogtaz.model.Request;

import java.time.LocalDateTime;

public record RequestEventResponseDTO(
        Long id, Request.Status previousStatus,
        Request.Status newStatus, LocalDateTime eventDate
) {}