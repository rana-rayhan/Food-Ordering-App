package com.food_ordering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_ordering.model.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long>{
    
}
