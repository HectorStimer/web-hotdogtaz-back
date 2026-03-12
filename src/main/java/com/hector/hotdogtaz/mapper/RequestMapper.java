package com.hector.hotdogtaz.mapper;

import com.hector.hotdogtaz.dto.request.Request.CreateItemRequestDTO;
import com.hector.hotdogtaz.dto.request.Request.CreateRequestDTO;
import com.hector.hotdogtaz.dto.response.RequestResponseDTO;
import com.hector.hotdogtaz.model.*;
import com.hector.hotdogtaz.repository.CommandRepository;
import com.hector.hotdogtaz.repository.IngredientRepository;
import com.hector.hotdogtaz.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestMapper {

    private final ItemRequestMapper itemRequestMapper;
    private final RequestEventMapper requestEventMapper;
    private final CommandRepository commandRepository;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;

    public RequestMapper(ItemRequestMapper itemRequestMapper, RequestEventMapper requestEventMapper,
                         CommandRepository commandRepository, ProductRepository productRepository,
                         IngredientRepository ingredientRepository) {
        this.itemRequestMapper = itemRequestMapper;
        this.requestEventMapper = requestEventMapper;
        this.commandRepository = commandRepository;
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public RequestResponseDTO toResponse(Request request) {
        if (request == null) {
            return null;
        }
        return new RequestResponseDTO(
                request.getId(),
                request.getStatus(),
                request.getObservation(),
                request.getOrderDate(),
                request.getCommand().getId(),
                request.getItems().stream().map(itemRequestMapper::toResponse).toList(),
                request.getEvents().stream().map(requestEventMapper::toResponse).toList()
        );
    }

    public Request fromCreate(CreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Command command = commandRepository.findById(dto.commandId())
                .orElseThrow(() -> new IllegalArgumentException("Command not found"));
        Request request = new Request(Request.Status.CREATED, dto.observation(), command);
        if (dto.items() != null) {
            List<ItemRequest> items = dto.items().stream().map(this::mapToItemRequest).toList();
            request.setItems(items);
            items.forEach(item -> item.setRequest(request));
        }
        return request;
    }

    private ItemRequest mapToItemRequest(CreateItemRequestDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        ItemRequest item = new ItemRequest(null, product, dto.quantity(), product.getPrice(), dto.observation());
        if (dto.ingredients() != null) {
            List<RequestItemIngredient> ingredients = dto.ingredients().stream()
                    .map(ia -> {
                        Ingredient ingredient = ingredientRepository.findById(ia.ingredientId())
                                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
                        return new RequestItemIngredient(item, ingredient, ia.action());
                    }).toList();
            item.setIngredients(ingredients);
        }
        return item;
    }
}
