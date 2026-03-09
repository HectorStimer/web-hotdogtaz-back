package com.hector.hotdogtaz.controller;


import com.hector.hotdogtaz.dto.request.Category.CreateCategoryDTO;
import com.hector.hotdogtaz.dto.response.CategoryResponseDTO;
import com.hector.hotdogtaz.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CreateCategoryDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));

    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody CreateCategoryDTO dto) {
        return ResponseEntity.ok(service.update(dto, id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}