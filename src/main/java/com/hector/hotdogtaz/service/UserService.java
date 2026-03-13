package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.User.CreateUserDTO;
import com.hector.hotdogtaz.dto.request.User.UpdateUserDTO;
import com.hector.hotdogtaz.dto.response.UserResponseDTO;
import com.hector.hotdogtaz.exception.BusinessException;
import com.hector.hotdogtaz.exception.ResourceNotFoundException;
import com.hector.hotdogtaz.mapper.UserMapper;
import com.hector.hotdogtaz.model.User;
import com.hector.hotdogtaz.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final UserMapper mapper;
    private final KeycloakUserService keycloakUserService;

    public UserService(UserRepository repository, UserMapper mapper, KeycloakUserService keycloakUserService) {
        this.repository = repository;
        this.mapper = mapper;
        this.keycloakUserService=keycloakUserService;
    }

    private void validateEmail(String email) {
        if (repository.existsByEmail(email))
            throw new BusinessException("Email already exists");
    }

    public UserResponseDTO save(CreateUserDTO dto) {
        logger.info("Creating a new User");
        validateEmail(dto.email());
        User user = new User(dto.name(), dto.email(), dto.password(), true, dto.type());
        keycloakUserService.createUser(user, dto.password());
        return mapper.toResponse(repository.save(user));
    }

    public UserResponseDTO update(UpdateUserDTO dto, Long id) {
        logger.info("Updating user {}", id);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setActive(dto.active());
        return mapper.toResponse(repository.save(user));
    }

    public void deactivate(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        user.setActive(false);
        repository.save(user);

        keycloakUserService.updateUserStatus(user.getEmail(), false);
    }

    public List<UserResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UserResponseDTO findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id)));
    }

    public List<UserResponseDTO> listByStatus(Boolean active) {
        List<User> users = active ? repository.findByActiveTrue() : repository.findAll();
        return users.stream().map(mapper::toResponse).toList();
    }
}