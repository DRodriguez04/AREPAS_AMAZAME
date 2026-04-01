package com.amazame.arepas.service;

import com.amazame.arepas.dto.ProductRequest;
import com.amazame.arepas.dto.ProductResponse;
import com.amazame.arepas.mapper.ProductMapper;
import com.amazame.arepas.model.Product;
import com.amazame.arepas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){

        Product product = ProductMapper.toEntity(productRequest);

        Product saved = productRepository.save(product);

        return ProductMapper.toResponse(saved);
    }

    public ProductResponse getProductById(Long id){

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return ProductMapper.toResponse(product);
    }

    public List<ProductResponse> getAllProducts(){

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public void deleteProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productRepository.delete(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest updatedProductRequest){

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existingProduct.setName(updatedProductRequest.getName());
        existingProduct.setType(updatedProductRequest.getType());
        existingProduct.setPrice(updatedProductRequest.getPrice());

        Product updated = productRepository.save(existingProduct);

        return ProductMapper.toResponse(updated);
    }
}
