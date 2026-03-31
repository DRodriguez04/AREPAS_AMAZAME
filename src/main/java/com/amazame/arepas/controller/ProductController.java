package com.amazame.arepas.controller;

import com.amazame.arepas.model.Product;
import com.amazame.arepas.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Productos", description = "API para gestión de productos")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Crear producto
    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    //Obtener un producto by Id
    @Operation(summary = "Obtener producto por ID")
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    //Listar todos los productos
    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //Eliminar un producto
    @Operation(summary = "Eliminar un producto")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
