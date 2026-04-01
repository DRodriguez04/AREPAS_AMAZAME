package com.amazame.arepas.service;

import com.amazame.arepas.model.Customer;
import com.amazame.arepas.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldCreateCustomerSuccessfully() {
        Customer customer = new Customer();
        customer.setName("Daniela");
        customer.setPhone("123456");
        customer.setAddress("Calle 123");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertNotNull(result);
        assertEquals("Daniela", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameIsMissing() {
        Customer customer = new Customer();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.createCustomer(customer);
        });

        assertEquals("El nombre es obligatorio", exception.getMessage());
    }
}
