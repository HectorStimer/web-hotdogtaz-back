package com.hector.hotdogtaz.dto.request.User;
import com.hector.hotdogtaz.model.User.UserType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateUserDTO(String name,
                            String email,
                            String password,
                            UserType type) {
}
