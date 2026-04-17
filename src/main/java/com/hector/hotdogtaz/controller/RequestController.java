package com.hector.hotdogtaz.controller;


import com.hector.hotdogtaz.dto.request.Request.AddItemRequestDTO;
import com.hector.hotdogtaz.dto.request.Request.CreateRequestDTO;
import com.hector.hotdogtaz.dto.request.Request.UpdateItemRequestDTO;
import com.hector.hotdogtaz.dto.request.Request.UpdateRequestDTO;
import com.hector.hotdogtaz.dto.response.ItemRequestResponseDTO;
import com.hector.hotdogtaz.dto.response.RequestResponseDTO;
import com.hector.hotdogtaz.service.ItemRequestService;
import com.hector.hotdogtaz.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {
    private final ItemRequestService itemRequestService;
    private final RequestService service;

    public RequestController(RequestService service, ItemRequestService itemRequestService){
        this.service=service;
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ResponseEntity<RequestResponseDTO> create(@Valid @RequestBody CreateRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody UpdateRequestDTO dto) {
        return ResponseEntity.ok(service.update(dto, id));
    }


    @GetMapping
    public ResponseEntity<List<RequestResponseDTO>> findAll(){
        return ResponseEntity.ok(service.listAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/queue")
    public ResponseEntity<List<RequestResponseDTO>> listQueue() {
        return ResponseEntity.ok(service.listQueue());
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<ItemRequestResponseDTO> addItem(@PathVariable Long id,
                                                          @Valid @RequestBody AddItemRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemRequestService.addItem(id, dto));
    }

    @PatchMapping("/{id}/items/{itemId}")
    public ResponseEntity<ItemRequestResponseDTO> updateItem(@PathVariable Long id,
                                                             @PathVariable Long itemId,
                                                             @Valid @RequestBody UpdateItemRequestDTO dto) {
        return ResponseEntity.ok(itemRequestService.updateItem(id, itemId, dto));
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id,
                                           @PathVariable Long itemId) {
        itemRequestService.removeItem(id, itemId);
        return ResponseEntity.noContent().build();
    }




}
