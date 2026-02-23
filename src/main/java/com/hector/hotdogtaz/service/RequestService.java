package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.Request.CreateRequestDTO;
import com.hector.hotdogtaz.dto.request.Request.UpdateRequestDTO;
import com.hector.hotdogtaz.dto.response.RequestResponseDTO;
import com.hector.hotdogtaz.mapper.RequestMapper;
import com.hector.hotdogtaz.model.Request;
import com.hector.hotdogtaz.repository.RequestRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class RequestService {
    private final static Logger logger =
            LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository repository;
    private final RequestMapper mapper;

    public RequestService(RequestRepository repository, RequestMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Request save(CreateRequestDTO dto) {
        logger.info("creating a new Request");
        Request request = RequestMapper.fromCreate(dto);
        return repository.save(request);
    }

    public Request update(UpdateRequestDTO dto, Long id) {
        Request request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("The request cannot found"));

        request.setStatus(dto.status());
        request.setEvents(dto.events());
        return repository.save(request);
    }

    public List<RequestResponseDTO> listAllRequests() {
        return repository.findAll().stream().map(RequestMapper::toResponse).toList();
    }

    public List<RequestResponseDTO> listStatusNotCompleted(){
        return repository.findByStatusNot(Request.Status.COMPLETED).stream()
                .map(RequestMapper::toResponse).toList();
    }

    public List<RequestResponseDTO> listStatusInPreparation(){
        return  repository.findByStatus(Request.Status.IN_PREPARATION).stream()
                .map(RequestMapper::toResponse).toList();
    }

    public List<RequestResponseDTO> listStatusCompleted(){
        return repository.findByStatus(Request.Status.COMPLETED).stream()
                .map(RequestMapper::toResponse).toList();
    }



}
