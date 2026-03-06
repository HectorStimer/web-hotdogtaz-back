package com.hector.hotdogtaz.dto.request.User;
import com.hector.hotdogtaz.model.User.UserType;

import java.time.LocalDate;

public record CreateUserDTO(String name,
                            String email,
                            String password,
                            UserType type) {
}
