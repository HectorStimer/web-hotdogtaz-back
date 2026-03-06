package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.Request.CreateRequestDTO;
import com.hector.hotdogtaz.dto.request.Request.UpdateRequestDTO;
import com.hector.hotdogtaz.dto.response.RequestResponseDTO;
import com.hector.hotdogtaz.mapper.RequestMapper;
import com.hector.hotdogtaz.model.*;
import com.hector.hotdogtaz.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RequestService {
    private final static Logger logger =
            LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository repository;
    private final CommandRepository commandRepository;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    private final RequestMapper mapper;

    public RequestService(RequestRepository repository,
                          CommandRepository commandRepository,
                          ProductRepository productRepository,
                          IngredientRepository ingredientRepository,
                          RequestMapper mapper) {
        this.repository = repository;
        this.commandRepository = commandRepository;
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.mapper = mapper;
    }

    public RequestResponseDTO save(CreateRequestDTO dto) {
        logger.info("Creating a new Request");

        Command command = commandRepository.findById(dto.commandId())
                .orElseThrow(() -> new RuntimeException("Command not found"));

        Request request = new Request(Request.Status.CREATED, dto.observation(), command);

        if (dto.items() != null) {
            dto.items().forEach(itemDTO -> {
                Product product = productRepository.findById(itemDTO.productId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                ItemRequest item = new ItemRequest(
                        request, product, itemDTO.quantity(),
                        product.getPrice(), itemDTO.observation()
                );

                if (itemDTO.ingredients() != null) {
                    itemDTO.ingredients().forEach(actionDTO -> {
                        Ingredient ingredient = ingredientRepository.findById(actionDTO.ingredientId())
                                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
                        item.getIngredients().add(
                                new RequestItemIngredient(item, ingredient, actionDTO.action())
                        );
                    });
                }

                request.getItems().add(item);
            });
        }

        return mapper.toResponse(repository.save(request));
    }

    public RequestResponseDTO update(UpdateRequestDTO dto, Long id) {
        logger.info("Updating request {}", id);

        Request request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Request.Status previous = request.getStatus();
        request.setStatus(dto.status());
        request.getEvents().add(new RequestEvent(previous, dto.status(), request));

        return mapper.toResponse(repository.save(request));
    }

    public RequestResponseDTO findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found")));
    }

    public List<RequestResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<RequestResponseDTO> listQueue() {
        return repository.findByStatusNot(Request.Status.COMPLETED).stream()
                .filter(r -> r.getStatus() != Request.Status.CANCELED)
                .map(mapper::toResponse)
                .toList();
    }
}