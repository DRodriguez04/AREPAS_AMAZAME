package com.amazame.arepas.mapper;

import com.amazame.arepas.dto.ProductRequest;
import com.amazame.arepas.dto.ProductResponse;
import com.amazame.arepas.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest productRequest) {
        return new Product(
                null,
                productRequest.getName(),
                productRequest.getType(),
                productRequest.getPrice()
        );
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
