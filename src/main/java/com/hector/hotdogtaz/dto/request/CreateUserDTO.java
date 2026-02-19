package com.hector.hotdogtaz.dto.request;
import com.hector.hotdogtaz.model.User.UserType;

import java.time.LocalDate;

public record CreateUserDTO(String name,
                            String email,
                            String password,
                            boolean active,
                            LocalDate createdAt,
                            UserType type) {
}
