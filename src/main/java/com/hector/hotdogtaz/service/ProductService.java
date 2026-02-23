package com.hector.hotdogtaz.service;


import com.hector.hotdogtaz.dto.request.Product.CreateProductDTO;
import com.hector.hotdogtaz.dto.request.Product.UpdateProductDTO;
import com.hector.hotdogtaz.dto.response.ProductResponseDTO;
import com.hector.hotdogtaz.mapper.ProductMapper;
import com.hector.hotdogtaz.model.Ingredient;
import com.hector.hotdogtaz.model.Product;
import com.hector.hotdogtaz.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final static Logger logger =
            LoggerFactory.getLogger(ProductService.class);


    private final ProductRepository repository;
    private final ProductMapper productMapper;


    public ProductService(ProductRepository repository, ProductMapper productMapper) {
        this.repository = repository;
        this.productMapper = productMapper;
    }

    public Product save(CreateProductDTO dto) {
        logger.info("creating a new product");

        Product product = new Product(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.category(),
                true,
                dto.imageUrl()
        );

        return repository.save(product);
    }


    public Product update(UpdateProductDTO dto, Long id){
        logger.info("updating {} product", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("product cannot found"));
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setActive(dto.active());
        product.setIngredients(dto.ingredients());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(dto.category());
        product.setPrice(dto.price());

        return repository.save(product);

    }

    @Transactional
    public Product deactivate(Long id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("product cannot found"));
        product.setActive(false);
        return repository.save(product);
    }

    public Page<ProductResponseDTO> listAllPaginated(Pageable pageable) {
        return repository.findAll(pageable)
                .map(productMapper::toResponse);
    }

    public Page<ProductResponseDTO> listProductsActivated(Pageable pageable){
        return repository.findByActiveTrue(pageable)
                .map(productMapper::toResponse);
    }

    public Page<ProductResponseDTO> listProductsByIngredients(
            Ingredient ingredient, Pageable pageable) {
        return repository
                .findByIngredientsIngredient(ingredient, pageable)
                .map(productMapper::toResponse);
    }

}
