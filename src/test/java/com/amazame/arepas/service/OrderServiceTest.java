package com.amazame.arepas.service;

import com.amazame.arepas.dto.OrderDetailRequest;
import com.amazame.arepas.dto.OrderRequest;
import com.amazame.arepas.dto.OrderResponse;
import com.amazame.arepas.model.Customer;
import com.amazame.arepas.model.Order;
import com.amazame.arepas.model.Product;
import com.amazame.arepas.repository.CustomerRepository;
import com.amazame.arepas.repository.OrderRepository;
import com.amazame.arepas.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Mock
    private CustomerRepository  customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void shouldCreateOrderSuccessfully(){

        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Daniela");
        customer.setPhone("123");
        customer.setAddress("Calle 1");

        Product product = new Product();
        product.setId(1L);
        product.setName("Arepa");
        product.setType("unidad");
        product.setPrice(5000.0);
        product.setActive(true);
        product.setStock(10);

        OrderDetailRequest detailRequest = new OrderDetailRequest();
        detailRequest.setProductId(1L);
        detailRequest.setQuantity(2);

        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);
        request.setType("LOCAL");
        request.setDetails(java.util.Arrays.asList(detailRequest));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        OrderResponse result = orderService.createOrder(request);

        // Assert
        assertNotNull(result);
        assertEquals("Daniela", result.getCustomerName());
        assertEquals(10000.0, result.getTotal(), 0.01);
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull(){

        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(request);
        });

        assertEquals("El tipo de pedido es obligatorio", exception.getMessage());
    }
}
