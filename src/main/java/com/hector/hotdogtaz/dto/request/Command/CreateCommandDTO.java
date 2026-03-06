package com.hector.hotdogtaz.dto.request.Command;

import com.hector.hotdogtaz.model.Request;

import java.util.List;

public record CreateCommandDTO(Integer number,
                               Integer tableNumber,
                               String observation) {
}
