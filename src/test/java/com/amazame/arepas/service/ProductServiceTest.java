package com.amazame.arepas.service;

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
        //Arrange
        Product product = new Product(null, "Arepa", "unidad", 5000.0);

        when(productRepository.save(product)).thenReturn(product);

        //Act
        Product result = productService.createProduct(product);

        //Assert
        assertNotNull(result);
        assertEquals("Arepa", result.getName());
        assertEquals(5000.0, result.getPrice());

        verify(productRepository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenPriceIsInvalid(){
        //Arrange
        Product product = new Product(null, "Arepa", "unidad", 0.0);

        //Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.createProduct(product));

        assertEquals("Price must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldReturnProductWhenIdExists(){
        //Arrange
        Product product = new Product(1L, "Arepa", "unidad", 5000.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        //Act
        Product result = productService.getProductById(1L);

        //Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Arepa", result.getName());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("Producto no encontrado", exception.getMessage());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldReturnAllProducts() {
        // Arrange
        List<Product> products = Arrays.asList(
                new Product(1L, "Arepa", "unidad", 5000.0),
                new Product(2L, "Jugo", "bebida", 3000.0));

        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(2, result.size());
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
}
