package com.amazame.arepas.controller;

import com.amazame.arepas.dto.OrderRequest;
import com.amazame.arepas.dto.OrderResponse;
import com.amazame.arepas.dto.OrderStatusRequest;
import com.amazame.arepas.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);
        request.setType("LOCAL");

        OrderResponse response = new OrderResponse();
        response.setId(1L);
        response.setTotal(10000.0);

        when(orderService.createOrder(any(OrderRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldReturnOrderById() throws Exception {

        OrderResponse response = new OrderResponse();
        response.setId(1L);
        response.setTotal(10000.0);

        when(orderService.getOrderById(1L)).thenReturn(response);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldReturnAllOrders() throws Exception {

        OrderResponse o1 = new OrderResponse();
        o1.setId(1L);

        OrderResponse o2 = new OrderResponse();
        o2.setId(2L);

        when(orderService.getAllOrders())
                .thenReturn(Arrays.asList(o1, o2));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateOrderStatus() throws Exception {

        OrderStatusRequest request = new OrderStatusRequest();
        request.setStatus("PAID");

        OrderResponse response = new OrderResponse();
        response.setId(1L);

        when(orderService.updateOrderStatus(eq(1L), eq("PAID")))
                .thenReturn(response);

        mockMvc.perform(patch("/orders/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
