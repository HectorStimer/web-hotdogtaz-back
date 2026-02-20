package com.hector.hotdogtaz.service;


import com.hector.hotdogtaz.dto.request.IngredientDTO;
import com.hector.hotdogtaz.model.Ingredient;
import com.hector.hotdogtaz.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    private final static Logger logger=
            LoggerFactory.getLogger(IngredientService.class);


    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository){
        this.repository=repository;
    }

    public Ingredient save(IngredientDTO dto){
        Ingredient ingredient = new Ingredient(dto.name());


        return repository.save(ingredient);

    }

    public Ingredient inactive(IngredientDTO dto ,Long id){
        Ingredient ingredient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("this ingredient cannot found"));
        ingredient.setActive(dto.active());

        return repository.save(ingredient);

    }

}
