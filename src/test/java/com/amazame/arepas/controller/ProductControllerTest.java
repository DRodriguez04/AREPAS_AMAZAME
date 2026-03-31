package com.amazame.arepas.controller;

import com.amazame.arepas.dto.ProductRequest;
import com.amazame.arepas.dto.ProductResponse;
import com.amazame.arepas.model.Product;
import com.amazame.arepas.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequest request = new ProductRequest();
        request.setName("Arepa");
        request.setType("unidad");
        request.setPrice(5000.0);

        ProductResponse response = new ProductResponse(1L, "Arepa", "unidad", 5000.0);

        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Arepa"));
    }

    @Test
    void shouldReturnBadRequestWhenProductIsInvalid() throws Exception {

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnProductById() throws Exception {

        ProductResponse product = new ProductResponse(1L, "Arepa", "unidad", 5000.0);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Arepa"));
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        // Arrange
        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(1L, "Arepa", "unidad", 5000.0),
                new ProductResponse(2L, "Jugo", "bebida", 3000.0)
        );

        when(productService.getAllProducts()).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateProduct() throws Exception {

        ProductRequest request = new ProductRequest();
        request.setName("Arepa con queso");
        request.setType("unidad");
        request.setPrice(6000.0);

        ProductResponse response = new ProductResponse(1L, "Arepa con queso", "unidad", 6000.0);

        when(productService.updateProduct(eq(1L), any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Arepa con queso"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }
}
