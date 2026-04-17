package com.hector.hotdogtaz.controller;


import com.hector.hotdogtaz.dto.request.Command.CreateCommandDTO;
import com.hector.hotdogtaz.dto.request.Command.UpdateCommandDTO;
import com.hector.hotdogtaz.dto.response.CommandResponseDTO;
import com.hector.hotdogtaz.service.CommandService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/commands")
public class CommandController {
    private final CommandService service;

    public CommandController(CommandService service){
        this.service=service;

    }

    @PostMapping
    public ResponseEntity<CommandResponseDTO> create( @Valid @RequestBody CreateCommandDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save((dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateCommandDTO dto){
        return ResponseEntity.ok(service.update(dto, id));
    }


    @GetMapping
    public ResponseEntity<Page<CommandResponseDTO>> listAll(Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
