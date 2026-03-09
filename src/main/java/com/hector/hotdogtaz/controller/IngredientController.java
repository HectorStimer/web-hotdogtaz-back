package com.hector.hotdogtaz.controller;


import com.hector.hotdogtaz.dto.request.Ingredient.IngredientDTO;
import com.hector.hotdogtaz.dto.response.IngredientResponseDTO;
import com.hector.hotdogtaz.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService service;


    public IngredientController(IngredientService service){
        this.service=service;

    }

    @PostMapping
    public ResponseEntity<IngredientResponseDTO> create(@RequestBody IngredientDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }


    @PostMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> deactive(@PathVariable Long id){
        return ResponseEntity.ok(service.deactivate(id));
    }


    @GetMapping
    public ResponseEntity<List<IngredientResponseDTO>> listAllIngredients(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
