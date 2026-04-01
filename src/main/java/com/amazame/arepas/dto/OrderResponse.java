package com.amazame.arepas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String customerName;

    private String type;

    private Double total;

    private Double deliveryFee;

    private List<OrderDetailResponse> details;
}
