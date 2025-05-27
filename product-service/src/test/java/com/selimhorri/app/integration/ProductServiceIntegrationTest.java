package com.selimhorri.app.integration;

import com.selimhorri.app.dto.CategoryDto;
import com.selimhorri.app.dto.ProductDto;
import com.selimhorri.app.repository.ProductRepository;
import com.selimhorri.app.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveAndFindProduct() {
        var categoryDto = CategoryDto.builder().categoryId(1).categoryTitle("Cat").build();
        var productDto = ProductDto.builder()
            .productTitle("IntegrationProd")
            .categoryDto(categoryDto)
            .build();

        var saved = productService.save(productDto);

        assertNotNull(saved.getProductId());
        assertEquals("IntegrationProd", saved.getProductTitle());

        var found = productService.findById(saved.getProductId());
        assertEquals("IntegrationProd", found.getProductTitle());
    }

    @Test
    void updateProduct_shouldModifyProduct() {
        var categoryDto = CategoryDto.builder().categoryId(1).categoryTitle("Cat").build();
        var productDto = ProductDto.builder()
            .productTitle("OriginalProd")
            .categoryDto(categoryDto)
            .build();

        var saved = productService.save(productDto);

        saved.setProductTitle("UpdatedProd");
        var updated = productService.update(saved);

        assertEquals("UpdatedProd", updated.getProductTitle());
    }

    @Test
    void deleteProduct_shouldRemoveProduct() {
        var categoryDto = CategoryDto.builder().categoryId(1).categoryTitle("Cat").build();
        var productDto = ProductDto.builder()
            .productTitle("ToDeleteProd")
            .categoryDto(categoryDto)
            .build();

        var saved = productService.save(productDto);
        Integer id = saved.getProductId();

        productService.deleteById(id);

        assertThrows(Exception.class, () -> productService.findById(id));
    }
}