package com.amazame.arepas.mapper;

import com.amazame.arepas.dto.OrderDetailResponse;
import com.amazame.arepas.dto.OrderResponse;
import com.amazame.arepas.model.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse toResponse(Order order){

        List<OrderDetailResponse> details = order.getDetails()
                .stream()
                .map(d -> new OrderDetailResponse(
                        d.getProduct().getId(),
                        d.getProduct().getName(),
                        d.getQuantity(),
                        d.getUnitPrice(),
                        d.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getCustomer().getName(),
                order.getType(),
                order.getTotal(),
                order.getDeliveryFee(),
                order.getStatus().name(),
                details
        );
    }
}
