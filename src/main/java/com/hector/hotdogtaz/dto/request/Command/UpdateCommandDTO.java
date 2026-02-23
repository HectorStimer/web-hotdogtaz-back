package com.hector.hotdogtaz.dto.request.Command;

import com.hector.hotdogtaz.model.Command;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateCommandDTO (Command.Status status,
                                BigDecimal total,
                                LocalDateTime closingDate){
}
