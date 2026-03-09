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

    public RequestResponseDTO save(CreateRequestDTO dto) {
        logger.info("creating a new Request");
        Request request = mapper.fromCreate(dto);
        return mapper.toResponse(repository.save(request));
    }

    public RequestResponseDTO update(UpdateRequestDTO dto, Long id) {
        Request request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("The request cannot found"));

        request.setStatus(dto.status());
        return mapper.toResponse(repository.save(request));
    }

    public List<RequestResponseDTO> listAllRequests() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public List<RequestResponseDTO> listAll() {
        return listAllRequests();
    }

    public RequestResponseDTO findById(Long id) {
        Request request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return mapper.toResponse(request);
    }

    public List<RequestResponseDTO> listQueue() {
        return repository.findAll().stream()
                .filter(r -> r.getStatus() == Request.Status.CREATED)
                .map(mapper::toResponse).toList();
    }
}
