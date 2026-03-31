package com.amazame.arepas.service;

import com.amazame.arepas.model.Product;
import com.amazame.arepas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){
        if (product.getPrice() == null || product.getPrice() <= 0){
            throw new RuntimeException("El precio debe ser mayor a 0");
        }

        if (product.getName() == null || product.getName().isEmpty()){
            throw new RuntimeException("El nombre es obligatorio");
        }

        return productRepository.save(product);
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void deleteProductById(Long id){
        if (!productRepository.existsById(id)){
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
