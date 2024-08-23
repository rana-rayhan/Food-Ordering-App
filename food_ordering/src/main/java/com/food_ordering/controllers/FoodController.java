package com.food_ordering.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.FoodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<AuthResponse<?>> searchRestaurant(@RequestParam String keyword) {
        try {
            return ResponseEntity.ok(foodService.searchFood(keyword));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/restaurant/id")
    public ResponseEntity<AuthResponse<?>> getRestaurantFood(
            @PathVariable Long id,
            @RequestParam boolean isVeg,
            @RequestParam boolean isNonVeg,
            @RequestParam boolean isSeasonal,
            @RequestParam(required = false) String food_category) {

        try {
            return ResponseEntity.ok(foodService.getRestaurantFoods(id, isVeg, isNonVeg, isSeasonal, food_category));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}