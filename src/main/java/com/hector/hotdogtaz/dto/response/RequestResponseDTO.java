package com.hector.hotdogtaz.dto.response;
import com.hector.hotdogtaz.model.ItemRequest;
import com.hector.hotdogtaz.model.Request.Status;
import com.hector.hotdogtaz.model.RequestEvent;

import java.time.LocalDateTime;
import java.util.List;


public record RequestResponseDTO(Long id,
                                 Status status,
                                 String observation,
                                 LocalDateTime orderDate,
                                 List<ItemRequest> items,
                                 List<RequestEvent> events) {
}
