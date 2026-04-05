package com.amazame.arepas.controller;

import com.amazame.arepas.dto.OrderRequest;
import com.amazame.arepas.dto.OrderResponse;
import com.amazame.arepas.dto.OrderStatusHistoryResponse;
import com.amazame.arepas.dto.OrderStatusRequest;
import com.amazame.arepas.service.OrderService;
import com.amazame.arepas.service.OrderStatusHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusHistoryService orderStatusHistoryService;

    @Operation(summary = "Dar de alta una orden")
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request){
        return orderService.createOrder(request);
    }

    @Operation(summary = "Obtener una orden por id")
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Listar todas las órdenes")
    @GetMapping
    public List<OrderResponse> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PatchMapping("/{id}/status")
    public OrderResponse updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid OrderStatusRequest request){

        return orderService.updateOrderStatus(id, request.getStatus());
    }

    @GetMapping("/{id}/history")
    public List<OrderStatusHistoryResponse> getOrderHistory(@PathVariable Long id){
        orderService.getOrderById(id); // para validar existencia de la orden
        return orderStatusHistoryService.getHistoryByOrderId(id);
    }
}
