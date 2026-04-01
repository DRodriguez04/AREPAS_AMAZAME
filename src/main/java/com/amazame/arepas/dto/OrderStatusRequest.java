package com.amazame.arepas.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class OrderStatusRequest {

    @NotEmpty(message = "El estado es obligatorio")
    private String status;
}
