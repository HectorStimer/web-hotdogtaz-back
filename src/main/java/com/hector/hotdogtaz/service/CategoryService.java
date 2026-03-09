package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.Category.CreateCategoryDTO;
import com.hector.hotdogtaz.dto.response.CategoryResponseDTO;
import com.hector.hotdogtaz.mapper.CategoryMapper;
import com.hector.hotdogtaz.model.Category;
import com.hector.hotdogtaz.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CategoryResponseDTO save(CreateCategoryDTO dto) {
        if (repository.existsByName(dto.name()))
            throw new RuntimeException("Category already exists");
        Category category = new Category(dto.name());
        return mapper.toResponse(repository.save(category));
    }

    public CategoryResponseDTO update(CreateCategoryDTO dto, Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.name());
        return mapper.toResponse(repository.save(category));
    }

    public CategoryResponseDTO findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found")));
    }

    public List<CategoryResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new RuntimeException("Category not found");
        repository.deleteById(id);
    }
}