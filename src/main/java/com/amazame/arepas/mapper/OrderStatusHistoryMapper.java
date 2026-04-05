package com.amazame.arepas.mapper;

import com.amazame.arepas.dto.OrderStatusHistoryResponse;
import com.amazame.arepas.model.OrderStatusHistory;

public class OrderStatusHistoryMapper {

    public static OrderStatusHistoryResponse toResponse(OrderStatusHistory history){
        return new OrderStatusHistoryResponse(
                history.getPreviousStatus().name(),
                history.getNextStatus().name(),
                history.getChangedAt()
        );
    }
}
