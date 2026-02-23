package com.hector.hotdogtaz.dto.request.Request;
import com.hector.hotdogtaz.model.Request.Status;
import com.hector.hotdogtaz.model.RequestEvent;

import java.util.List;


public record UpdateRequestDTO(Status status, List<RequestEvent> events ) {
}
