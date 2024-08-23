package com.food_ordering.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_ordering.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findByRestaurantId(Long restaurantId);

}
