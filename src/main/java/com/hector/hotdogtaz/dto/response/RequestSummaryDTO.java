package com.hector.hotdogtaz.dto.response;


import com.hector.hotdogtaz.model.Request;

import java.time.LocalDateTime;

public record RequestSummaryDTO(
        Long id, Request.Status status, LocalDateTime orderDate
) {}