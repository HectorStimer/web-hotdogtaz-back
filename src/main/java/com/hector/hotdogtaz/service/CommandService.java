package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.Command.CreateCommandDTO;
import com.hector.hotdogtaz.dto.request.Command.UpdateCommandDTO;
import com.hector.hotdogtaz.model.Command;
import com.hector.hotdogtaz.repository.CommandRepository;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class CommandService {
    private final static Logger logger=
            LoggerFactory.getLogger(CommandService.class);

    private final CommandRepository repository;

    public CommandService(CommandRepository repository){
        this.repository=repository;
    }

    public Command save(CreateCommandDTO dto) {
        Command command = new Command(
                dto.number(),
                dto.tableNumber(),
                dto.observation()
        );

        return repository.save(command);
    }

    public Command update(UpdateCommandDTO dto, Long id){
        Command command = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("this command cannot exists"));

        command.setStatus(dto.status());
        command.setTotal(dto.total());
        command.setClosingDate(dto.closingDate());
        return repository.save(command);
    }




}
