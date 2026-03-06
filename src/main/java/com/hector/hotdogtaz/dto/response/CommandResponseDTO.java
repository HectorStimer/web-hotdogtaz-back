package com.hector.hotdogtaz.dto.response;
import com.hector.hotdogtaz.model.Command.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CommandResponseDTO (Long id,
                                  Integer number,
                                  Integer tableNumber,
                                  Status status,
                                  BigDecimal total,
                                  String observation,
                                  LocalDateTime openingDate,
                                  LocalDateTime closingDate,
                                  List<RequestSummaryDTO> requests){
}
