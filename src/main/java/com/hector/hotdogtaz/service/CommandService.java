package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.Command.CreateCommandDTO;
import com.hector.hotdogtaz.dto.request.Command.UpdateCommandDTO;
import com.hector.hotdogtaz.dto.response.CommandResponseDTO;
import com.hector.hotdogtaz.mapper.CommandMapper;
import com.hector.hotdogtaz.model.Command;
import com.hector.hotdogtaz.repository.CommandRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommandService {
    private final CommandRepository repository;
    private final CommandMapper mapper;

    public CommandService(CommandRepository repository, CommandMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CommandResponseDTO save(CreateCommandDTO dto) {
        Command command = new Command(dto.number(), dto.tableNumber(), dto.observation());
        return mapper.toResponse(repository.save(command));
    }

    public CommandResponseDTO update(UpdateCommandDTO dto, Long id) {
        Command command = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Command not found"));

        command.setStatus(dto.status());

        if (dto.status() == Command.Status.COMPLETED) {
            command.setClosingDate(LocalDateTime.now());
            command.recalculateTotal();
        }

        return mapper.toResponse(repository.save(command));
    }

    public CommandResponseDTO findById(Long id) {
        Command command = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Command not found"));
        return mapper.toResponse(command);
    }

    public Page<CommandResponseDTO> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }
}