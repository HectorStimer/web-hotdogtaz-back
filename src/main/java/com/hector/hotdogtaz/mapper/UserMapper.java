package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.response.UserResponseDTO;
import com.hector.hotdogtaz.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toResponse(User user) {
        if (user == null) return null;
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getCreatedAt(),
                user.getType()
        );
    }
}