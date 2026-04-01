package com.amazame.arepas.mapper;

import com.amazame.arepas.dto.ProductRequest;
import com.amazame.arepas.dto.ProductResponse;
import com.amazame.arepas.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest productRequest) {

        Product product = new Product();

        product.setName(productRequest.getName());
        product.setType(productRequest.getType());
        product.setPrice(productRequest.getPrice());

        // valores por defecto
        product.setActive(true);
        product.setStock(0);

        return product;
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getType(),
                product.getPrice()
        );
    }
}
