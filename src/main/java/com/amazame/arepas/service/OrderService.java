package com.amazame.arepas.service;

import com.amazame.arepas.model.Order;
import com.amazame.arepas.repository.OrderRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {

        if(order.getType() == null || order.getType().isEmpty()){
            throw new RuntimeException("El tipo de pedido es obligatorio");
        }

        if (order.getDetails() == null || order.getDetails().isEmpty()){
            throw new RuntimeException("El pedido debe tener al menos un producto");
        }

        double total = 0.0;

        for (var detail: order.getDetails()){
            double subtotal = detail.getPrice() * detail.getQuantity();
            total += subtotal;

            detail.setOrder(order);
        }

        order.setTotal(total);
        order.setCreatedAt(LocalDateTime.now());

        if(order.getType().equalsIgnoreCase("DOMICILIO")){
            if(order.getAddress() == null || order.getAddress().isEmpty()){
                throw new RuntimeException("La dirección es obligatoria para domicilios");
            }
        }

        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
}
