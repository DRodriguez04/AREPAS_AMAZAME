package com.amazame.arepas.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderRequest {

    @NotNull(message = "El cliente es obligatorio")
    private Long customerId;

    @NotNull(message = "El tipo de pedido es obligatorio")
    private String type; // local o domicilio

    @NotEmpty(message = "El pedido es inválido")
    private List<OrderDetailRequest> details;
}
