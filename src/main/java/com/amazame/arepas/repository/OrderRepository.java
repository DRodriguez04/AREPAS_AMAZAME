package com.amazame.arepas.repository;

import com.amazame.arepas.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
