package com.amazame.arepas.service;

import com.amazame.arepas.exception.NotFoundException;
import com.amazame.arepas.model.Customer;
import com.amazame.arepas.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {

        if(customer.getName() == null || customer.getName().isEmpty()){
            throw new RuntimeException("El nombre es obligatorio");
        }

        if(customer.getPhone() == null || customer.getPhone().isEmpty()){
            throw new RuntimeException("El teléfono es obligatorio");
        }

        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
}
