package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.Request.AddItemRequestDTO;
import com.hector.hotdogtaz.dto.request.Request.UpdateItemRequestDTO;
import com.hector.hotdogtaz.dto.response.ItemRequestResponseDTO;
import com.hector.hotdogtaz.exception.BusinessException;
import com.hector.hotdogtaz.exception.ResourceNotFoundException;
import com.hector.hotdogtaz.mapper.ItemRequestMapper;
import com.hector.hotdogtaz.model.*;
import com.hector.hotdogtaz.repository.IngredientRepository;
import com.hector.hotdogtaz.repository.ProductRepository;
import com.hector.hotdogtaz.repository.RequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItemRequestService {

    private final RequestRepository requestRepository;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    private final ItemRequestMapper mapper;

    public ItemRequestService(RequestRepository requestRepository,
                              ProductRepository productRepository,
                              IngredientRepository ingredientRepository,
                              ItemRequestMapper mapper) {
        this.requestRepository = requestRepository;
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.mapper = mapper;
    }

    public ItemRequestResponseDTO addItem(Long requestId, AddItemRequestDTO dto) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request", requestId));

        if (request.getStatus() == Request.Status.COMPLETED ||
                request.getStatus() == Request.Status.CANCELED) {
            throw new BusinessException("Cannot add item to a finished request");
        }

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", dto.productId()));

        ItemRequest item = new ItemRequest(
                request, product, dto.quantity(),
                product.getPrice(), dto.observation()
        );

        if (dto.ingredients() != null) {
            dto.ingredients().forEach(actionDTO -> {
                Ingredient ingredient = ingredientRepository.findById(actionDTO.ingredientId())
                        .orElseThrow(() -> new ResourceNotFoundException("Ingredient", actionDTO.ingredientId()));
                item.getIngredients().add(
                        new RequestItemIngredient(item, ingredient, actionDTO.action())
                );
            });
        }

        request.getItems().add(item);
        requestRepository.save(request);
        return mapper.toResponse(item);
    }

    public ItemRequestResponseDTO updateItem(Long requestId, Long itemId,
                                             UpdateItemRequestDTO dto) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request", requestId));

        ItemRequest item = request.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Item not found in this request"));

        item.setQuantity(dto.quantity());
        item.setObservation(dto.observation());

        requestRepository.save(request);
        return mapper.toResponse(item);
    }

    public void removeItem(Long requestId, Long itemId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request", requestId));

        boolean removed = request.getItems()
                .removeIf(i -> i.getId().equals(itemId));

        if (!removed)
            throw new BusinessException("Item not found in this request");

        requestRepository.save(request);
    }
}