package com.amazame.arepas.controller;

import com.amazame.arepas.dto.ProductRequest;
import com.amazame.arepas.dto.ProductResponse;
import com.amazame.arepas.mapper.ProductMapper;
import com.amazame.arepas.model.Product;
import com.amazame.arepas.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Productos", description = "API para gestión de productos")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Crear producto
    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    //Obtener un producto by Id
    @Operation(summary = "Obtener producto por ID")
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    //Listar todos los productos
    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    //Actualizar un producto
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @Valid @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id, productRequest);
    }

    //Eliminar un producto
    @Operation(summary = "Eliminar un producto")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
