package com.amazame.arepas.service;

import com.amazame.arepas.dto.ProductRequest;
import com.amazame.arepas.dto.ProductResponse;
import com.amazame.arepas.model.Product;
import com.amazame.arepas.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProductSuccessfully(){

        ProductRequest request = new ProductRequest();
        request.setName("Arepa");
        request.setType("unidad");
        request.setPrice(5000.0);

        Product saved = new Product(1L, "Arepa", "unidad", 5000.0);

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        ProductResponse result = productService.createProduct(request);

        assertNotNull(result);
        assertEquals("Arepa", result.getName());
        assertEquals(5000.0, result.getPrice(), 0.01);
    }

    @Test
    void shouldReturnProductWhenIdExists(){

        Product product = new Product(1L, "Arepa", "unidad", 5000.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Arepa", result.getName());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        ProductRequest request = new ProductRequest();
        request.setName("Arepa");
        request.setType("unidad");
        request.setPrice(5000.0);

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1L, request);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void shouldReturnAllProducts() {

        List<Product> products = Arrays.asList(
                new Product(1L, "Arepa", "unidad", 5000.0),
                new Product(2L, "Jugo", "bebida", 3000.0)
        );

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponse> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Arepa", result.get(0).getName());

        verify(productRepository).findAll();
    }

    @Test
    void shouldDeleteProductWhenIdExists() {
        // Arrange
        Long id = 1L;

        when(productRepository.existsById(id)).thenReturn(true);

        // Act
        productService.deleteProductById(id);

        // Assert
        verify(productRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        // Arrange
        Long id = 1L;

        when(productRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProductById(id);
        });

        assertEquals("Producto no encontrado", exception.getMessage());

        verify(productRepository, never()).deleteById(id);
    }

    @Test
    void shouldUpdateProductSuccessfully(){

        Product existing = new Product(1L, "Arepa", "unidad", 5000.0);

        ProductRequest request = new ProductRequest();
        request.setName("Arepa con queso");
        request.setType("unidad");
        request.setPrice(6000.0);

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(existing);

        ProductResponse result = productService.updateProduct(1L, request);

        assertEquals("Arepa con queso", result.getName());
        assertEquals(6000.0, result.getPrice(), 0.01);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct(){

        ProductRequest request = new ProductRequest();
        request.setName("Arepa");
        request.setType("unidad");
        request.setPrice(5000.0);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1L, request);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }
}
