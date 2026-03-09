package com.hector.hotdogtaz.controller;

import com.hector.hotdogtaz.dto.request.User.CreateUserDTO;
import com.hector.hotdogtaz.dto.request.User.UpdateUserDTO;
import com.hector.hotdogtaz.dto.response.UserResponseDTO;
import com.hector.hotdogtaz.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody CreateUserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody UpdateUserDTO dto) {
        return ResponseEntity.ok(service.update(dto, id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/status")
    public ResponseEntity<List<UserResponseDTO>> listByStatus(@RequestParam Boolean active) {
        return ResponseEntity.ok(service.listByStatus(active));
    }
}