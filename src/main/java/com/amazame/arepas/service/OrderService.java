package com.amazame.arepas.service;

import com.amazame.arepas.dto.OrderDetailRequest;
import com.amazame.arepas.dto.OrderRequest;
import com.amazame.arepas.dto.OrderResponse;
import com.amazame.arepas.enums.OrderStatus;
import com.amazame.arepas.exception.BadRequestException;
import com.amazame.arepas.mapper.OrderMapper;
import com.amazame.arepas.model.Customer;
import com.amazame.arepas.model.Order;
import com.amazame.arepas.model.OrderDetail;
import com.amazame.arepas.model.Product;
import com.amazame.arepas.repository.CustomerRepository;
import com.amazame.arepas.repository.OrderRepository;
import com.amazame.arepas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request){

        // 1. Validación de negocio (NO de formato)
        if(!"LOCAL".equalsIgnoreCase(request.getType()) &&
                !"DOMICILIO".equalsIgnoreCase(request.getType())){
            throw new BadRequestException("Tipo de pedido inválido");
        }

        // Búsqueda cliente
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado"));

        // 3. Crear orden
        Order order = new Order();
        order.setCustomer(customer);
        order.setType(request.getType());
        order.setCreatedAt(java.time.LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setDeliveryFee(0.0);
        order.setUpdatedAt(java.time.LocalDateTime.now());

        List<OrderDetail> details = new ArrayList<>();
        double total = 0.0;

        // 4. Procesar productos
        for(OrderDetailRequest d : request.getDetails()){

            Product product = productRepository.findById(d.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if(product.getStock() == null || product.getStock() < d.getQuantity()){
                throw new BadRequestException("Stock insuficiente para el producto: " + product.getName());
            }

            product.setStock(product.getStock() - d.getQuantity());

            double unitPrice = product.getPrice();
            double subtotal = unitPrice * d.getQuantity();

            OrderDetail detail = new OrderDetail();
            detail.setProduct(product);
            detail.setQuantity(d.getQuantity());
            detail.setUnitPrice(unitPrice);
            detail.setSubtotal(subtotal);
            detail.setOrder(order);

            details.add(detail);
            total += subtotal;
        }

        // 5. Asignar lista y total
        order.setDetails(details);
        order.setTotal(total);

        // 6. Guardar
        Order savedOrder = orderRepository.save(order);

        // 7. Mapear respuesta
        return OrderMapper.toResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        return OrderMapper.toResponse(order);
    }

    public List<OrderResponse> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    public OrderResponse updateOrderStatus(Long id, String newStatus){

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        OrderStatus newStatusEnum;

        try {
            newStatusEnum = OrderStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado inválido");
        }

        validateStatusTransition(order.getStatus(), newStatusEnum);

        order.setStatus(newStatusEnum);
        order.setUpdatedAt(java.time.LocalDateTime.now());

        return OrderMapper.toResponse(orderRepository.save(order));
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus){

        if(OrderStatus.CREATED.equals(currentStatus)){
            if(!OrderStatus.PAID.equals(newStatus) &&
                    !OrderStatus.CANCELLED.equals(newStatus)){
                throw new RuntimeException("Transición de estado inválida");
            }
            return;
        }

        if(OrderStatus.PAID.equals(currentStatus)){
            if(!OrderStatus.PREPARING.equals(newStatus)){
                throw new RuntimeException("Transición de estado inválida");
            }
            return;
        }

        if(OrderStatus.PREPARING.equals(currentStatus)){
            if(!OrderStatus.DELIVERED.equals(newStatus)){
                throw new RuntimeException("Transición de estado inválida");
            }
            return;
        }

        throw new RuntimeException("No se puede modificar el pedido en este estado");
    }
}
