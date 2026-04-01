package com.amazame.arepas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name; // Ej: arepa de maíz

    @NotBlank(message = "El tipo es obligatorio")
    private String type; // Ej: unidad, paquete, bebida, queso

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double price;

    private Boolean active = true;

    private Integer stock;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}
