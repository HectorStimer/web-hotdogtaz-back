package com.hector.hotdogtaz.service;


import com.hector.hotdogtaz.dto.request.Command.CreateCommandDTO;
import com.hector.hotdogtaz.dto.response.CommandResponseDTO;
import com.hector.hotdogtaz.mapper.CommandMapper;
import com.hector.hotdogtaz.model.Command;
import com.hector.hotdogtaz.repository.CommandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandServiceTest {

    @Mock
    private CommandRepository repository;

    @Mock
    private CommandMapper mapper;

    @InjectMocks
    private CommandService service;

    private Command command;
    private CommandResponseDTO responseDTO;

    @Test
    @DisplayName("deve salvar a comanda")
    void save_sucess(){
        CreateCommandDTO dto = new CreateCommandDTO(1, 4, null);
        when(repository.existsByNumber(1)).thenReturn(false);
    }

}
