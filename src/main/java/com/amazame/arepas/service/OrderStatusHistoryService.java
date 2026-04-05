package com.amazame.arepas.service;

import com.amazame.arepas.dto.OrderStatusHistoryResponse;
import com.amazame.arepas.enums.OrderStatus;
import com.amazame.arepas.mapper.OrderStatusHistoryMapper;
import com.amazame.arepas.model.Order;
import com.amazame.arepas.model.OrderStatusHistory;
import com.amazame.arepas.repository.OrderStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStatusHistoryService {

    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    public void saveHistory(Order order, OrderStatus previous, OrderStatus next){

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setPreviousStatus(previous);
        history.setNextStatus(next);
        history.setChangedAt(LocalDateTime.now());
        history.setChangedBy("SYSTEM"); // luego se mejora

        orderStatusHistoryRepository.save(history);
    }

    public List<OrderStatusHistoryResponse> getHistoryByOrderId(Long orderId){
        return orderStatusHistoryRepository.findByOrderId(orderId)
                .stream()
                .map(OrderStatusHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
