package com.amazame.arepas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;
    private String type;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order",  cascade = CascadeType.ALL)
    private List<OrderDetail> details;

    @ManyToOne
    private Customer customer;
}
