package com.hector.hotdogtaz.dto.response;
import com.hector.hotdogtaz.model.ItemRequest;
import com.hector.hotdogtaz.model.Request;
import com.hector.hotdogtaz.model.Request.Status;
import com.hector.hotdogtaz.model.RequestEvent;

import java.time.LocalDateTime;
import java.util.List;


public record RequestResponseDTO(Long id,
                                 Request.Status status,
                                 String observation,
                                 LocalDateTime orderDate,
                                 Long commandId,
                                 List<ItemRequestResponseDTO> items,
                                 List<RequestEventResponseDTO> events) {
}
