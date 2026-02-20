package com.hector.hotdogtaz.repository;

import com.hector.hotdogtaz.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository <Ingredient, Long > {
}
