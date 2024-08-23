package com.food_ordering.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.food_ordering.model.Food;

public interface FoodRepo extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantId(Long id);

    @Query("SELECT r FROM Food r WHERE LOWER(r.name) LIKE %:query% OR LOWER(r.foodCategory.name) LIKE %:query%")
    List<Food> findBySearchQuery(String query);

}
