package com.amazame.arepas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistoryResponse {

    private String previousStatus;
    private String newStatus;
    private LocalDateTime changedAt;
}
