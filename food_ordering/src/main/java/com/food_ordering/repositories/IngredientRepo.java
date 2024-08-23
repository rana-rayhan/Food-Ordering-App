package com.food_ordering.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food_ordering.model.IngredientsItem;

@Repository
public interface IngredientRepo extends JpaRepository<IngredientsItem, Long> {
    List<IngredientsItem> findByRestaurantId(Long id);
}
