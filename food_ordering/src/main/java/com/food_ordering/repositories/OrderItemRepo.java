package com.food_ordering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_ordering.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
