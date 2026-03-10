package com.hector.hotdogtaz.service;


import com.hector.hotdogtaz.dto.request.Ingredient.IngredientDTO;
import com.hector.hotdogtaz.dto.response.IngredientResponseDTO;
import com.hector.hotdogtaz.exception.ResourceNotFoundException;
import com.hector.hotdogtaz.mapper.IngredientMapper;
import com.hector.hotdogtaz.model.Ingredient;
import com.hector.hotdogtaz.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final static Logger logger=
            LoggerFactory.getLogger(IngredientService.class);


    private final IngredientRepository repository;
    private final IngredientMapper mapper;

    public IngredientService(IngredientRepository repository, IngredientMapper mapper){

        this.repository=repository;
        this.mapper=mapper;
    }

    public IngredientResponseDTO save(IngredientDTO dto) {
        Ingredient ingredient = new Ingredient(dto.name());
        return mapper.toResponse(repository.save(ingredient));
    }


    public IngredientResponseDTO deactivate(Long id) {
        Ingredient ingredient = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", id));
        ingredient.setActive(false);
        return mapper.toResponse(repository.save(ingredient));
    }

    public List<IngredientResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public IngredientResponseDTO findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", id)));
    }

}
