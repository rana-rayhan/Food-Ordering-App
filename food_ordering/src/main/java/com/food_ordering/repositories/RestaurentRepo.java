package com.food_ordering.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.food_ordering.model.Restaurant;

public interface RestaurentRepo extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByOwnerId(Long userId);

    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE %:query% OR LOWER(r.description) LIKE %:query%")
    List<Restaurant> findBySearchQuery(String query);

}
