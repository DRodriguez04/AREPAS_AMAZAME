package com.amazame.arepas.model;

import com.amazame.arepas.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order",  cascade = CascadeType.ALL)
    private List<OrderDetail> details;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double deliveryFee;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
