package com.amazame.arepas.service;

import com.amazame.arepas.model.Order;
import com.amazame.arepas.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrderSuccessfully(){
        Order order = new Order(null, 10000.0, "LOCAL", "Daniela", null, null);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldCalculateTotalCorrectly() {

        OrderDetail d1 = new OrderDetail(null, 2, 5000.0, null, null);
        OrderDetail d2 = new OrderDetail(null, 1, 3000.0, null, null);

        Order order = new Order();
        order.setType("LOCAL");
        order.setDetails(java.util.Arrays.asList(d1, d2));

        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        Order result = orderService.createOrder(order);

        assertEquals(13000.0, result.getTotal(), 0.01);
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull(){
        Order order = new Order();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(order));

        assertEquals("El tipo de pedido es obligatorio", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAddressIsMissingForDelivery() {
        Order order = new Order(null, 10000.0, "DOMICILIO", "Daniela", null, null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(order);
        });

        assertEquals("La dirección es obligatoria para domicilios", exception.getMessage());
    }
}
