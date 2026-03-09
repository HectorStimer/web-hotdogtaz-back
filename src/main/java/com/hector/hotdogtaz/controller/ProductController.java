package com.hector.hotdogtaz.controller;


import com.hector.hotdogtaz.dto.request.Product.CreateProductDTO;
import com.hector.hotdogtaz.dto.request.Product.UpdateProductDTO;
import com.hector.hotdogtaz.dto.response.ProductResponseDTO;
import com.hector.hotdogtaz.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController{
    private final ProductService service;

    public ProductController(ProductService service){
        this.service=service;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody CreateProductDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id,
                                                     @RequestBody UpdateProductDTO dto){
        return ResponseEntity.ok(service.update(dto, id));
    }


    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ProductResponseDTO> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(service.deactivate(id));
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> listAllProducts(Pageable pageable){
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ProductResponseDTO>> listActivated(Pageable pageable) {
        return ResponseEntity.ok(service.listActivated(pageable));
    }
}
