package com.food_ordering.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_ordering.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long userId);

    List<Order> findByRestaurantId(Long userId);
}
