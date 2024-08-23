package com.food_ordering.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food_ordering.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByCustomerId(Long customerId);
}
