package com.hector.hotdogtaz.dto.response;

import com.hector.hotdogtaz.model.User.UserType;

import java.time.LocalDate;

public record UserResponseDTO(Long id,
                              String name,
                              String email,
                              boolean active,
                              LocalDate createdAt,
                              UserType type) {
}
