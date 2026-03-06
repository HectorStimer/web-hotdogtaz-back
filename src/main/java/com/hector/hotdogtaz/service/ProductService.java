package com.hector.hotdogtaz.service;


import com.hector.hotdogtaz.dto.request.Product.CreateProductDTO;
import com.hector.hotdogtaz.dto.request.Product.UpdateProductDTO;
import com.hector.hotdogtaz.dto.response.ProductResponseDTO;
import com.hector.hotdogtaz.mapper.ProductMapper;
import com.hector.hotdogtaz.model.Category;
import com.hector.hotdogtaz.model.Ingredient;
import com.hector.hotdogtaz.model.Product;
import com.hector.hotdogtaz.model.ProductIngredient;
import com.hector.hotdogtaz.repository.CategoryRepository;
import com.hector.hotdogtaz.repository.IngredientRepository;
import com.hector.hotdogtaz.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductMapper mapper;


    public ProductService(ProductRepository repository,
                          CategoryRepository categoryRepository,
                          IngredientRepository ingredientRepository,
                          ProductMapper mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
        this.mapper = mapper;
    }





    public ProductResponseDTO save(CreateProductDTO dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product(
                dto.name(), dto.description(), dto.price(),
                category, true, dto.imageUrl()
        );


        if (dto.ingredientId() != null) {
            dto.ingredientId().forEach(ingredientId -> {
                Ingredient ingredient = ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new RuntimeException("Ingredient not found: " + ingredientId));
                product.getIngredients().add(new ProductIngredient(product, ingredient));
            });
        }

        return mapper.toResponse(repository.save(product));
    }




    public ProductResponseDTO update(UpdateProductDTO dto, Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setActive(dto.active());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(category);


        product.getIngredients().clear();
        dto.ingredientId().forEach(ingredientId -> {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new RuntimeException("Ingredient not found: " + ingredientId));
            product.getIngredients().add(new ProductIngredient(product, ingredient));
        });

        return mapper.toResponse(repository.save(product));
    }





    public ProductResponseDTO deactivate(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setActive(false);
        return mapper.toResponse(repository.save(product));
    }




    public Page<ProductResponseDTO> listByIngredient(Long ingredientId, Pageable pageable) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
        return repository.findByIngredientsIngredient(ingredient, pageable)
                .map(mapper::toResponse);
    }
    public Page<ProductResponseDTO> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public Page<ProductResponseDTO> listActivated(Pageable pageable) {
        return repository.findByActiveTrue(pageable).map(mapper::toResponse);
    }
}
