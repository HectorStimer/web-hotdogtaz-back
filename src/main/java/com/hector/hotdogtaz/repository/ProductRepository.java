package com.hector.hotdogtaz.repository;

import com.hector.hotdogtaz.model.Ingredient;
import com.hector.hotdogtaz.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long>{

    Page<Product> findByIngredientsIngredient(Ingredient ingredient, Pageable pageable);

    Page<Product> findByActiveTrue(Pageable pageable);
}
