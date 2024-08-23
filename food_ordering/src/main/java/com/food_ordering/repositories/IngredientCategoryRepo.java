package com.food_ordering.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_ordering.model.IngredientsCategory;

public interface IngredientCategoryRepo extends JpaRepository<IngredientsCategory, Long> {
    List<IngredientsCategory> findByRestaurantId(Long id);
}
