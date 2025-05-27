package com.selimhorri.app.unit;

import com.selimhorri.app.dto.CategoryDto;
import com.selimhorri.app.dto.ProductDto;
import com.selimhorri.app.domain.Product;
import com.selimhorri.app.helper.ProductMappingHelper;
import com.selimhorri.app.repository.ProductRepository;
import com.selimhorri.app.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnProductList() {
        var categoryDto = CategoryDto.builder().categoryId(1).categoryTitle("Cat").build();
        var productDto = ProductDto.builder().productId(1).productTitle("Prod").categoryDto(categoryDto).build();
        var product = ProductMappingHelper.map(productDto);

        when(productRepository.findAll()).thenReturn(List.of(product));

        var result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals("Prod", result.get(0).getProductTitle());
    }

    @Test
    void findById_shouldReturnProduct() {
        var categoryDto = CategoryDto.builder().categoryId(1).categoryTitle("Cat").build();
        var productDto = ProductDto.builder().productId(1).productTitle("Prod").categoryDto(categoryDto).build();
        var product = ProductMappingHelper.map(productDto);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        var result = productService.findById(1);

        assertEquals("Prod", result.getProductTitle());
    }

    @Test
    void save_shouldReturnSavedProduct() {
        var categoryDto = CategoryDto.builder().categoryId(1).categoryTitle("Cat").build();
        var productDto = ProductDto.builder().productId(1).productTitle("Prod").categoryDto(categoryDto).build();
        var product = ProductMappingHelper.map(productDto);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        var result = productService.save(productDto);

        assertEquals("Prod", result.getProductTitle());
    }

}