package com.hector.hotdogtaz.repository;

import com.hector.hotdogtaz.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long> {
}
