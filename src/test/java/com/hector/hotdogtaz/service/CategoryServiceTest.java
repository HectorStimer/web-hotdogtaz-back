package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.Category.CreateCategoryDTO;
import com.hector.hotdogtaz.dto.response.CategoryResponseDTO;
import com.hector.hotdogtaz.exception.ResourceNotFoundException;
import com.hector.hotdogtaz.mapper.CategoryMapper;
import com.hector.hotdogtaz.model.Category;
import com.hector.hotdogtaz.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService service;

    private Category category;
    private CategoryResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        category = new Category("Hot Dog");
        responseDTO = new CategoryResponseDTO(1L, "Hot Dog");
    }

    @Test
    @DisplayName("Deve salvar categoria com sucesso")
    void save_success() {
        CreateCategoryDTO dto = new CreateCategoryDTO("Hot Dog");

        when(repository.existsByName("Hot Dog")).thenReturn(false);
        when(repository.save(any(Category.class))).thenReturn(category);
        when(mapper.toResponse(category)).thenReturn(responseDTO);

        CategoryResponseDTO result = service.save(dto);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Hot Dog");
        verify(repository).save(any(Category.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar categoria com nome duplicado")
    void save_duplicateName_throwsException() {
        CreateCategoryDTO dto = new CreateCategoryDTO("Hot Dog");

        when(repository.existsByName("Hot Dog")).thenReturn(true);

        assertThatThrownBy(() -> service.save(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Category already exists");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar categoria por ID")
    void findById_success() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));
        when(mapper.toResponse(category)).thenReturn(responseDTO);

        CategoryResponseDTO result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Hot Dog");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar categoria inexistente")
    void findById_notFound_throwsException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Deve retornar lista de categorias")
    void listAll_success() {
        when(repository.findAll()).thenReturn(List.of(category));
        when(mapper.toResponse(category)).thenReturn(responseDTO);

        List<CategoryResponseDTO> result = service.listAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Hot Dog");
    }

    @Test
    @DisplayName("Deve deletar categoria com sucesso")
    void delete_success() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar categoria inexistente")
    void delete_notFound_throwsException() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).deleteById(any());
    }
}