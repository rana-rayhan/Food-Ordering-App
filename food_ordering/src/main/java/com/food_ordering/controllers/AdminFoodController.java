package com.food_ordering.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.request.FoodReq;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.FoodService;
import com.food_ordering.services.RestaurentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/food")
@RequiredArgsConstructor
public class AdminFoodController {
    private final FoodService foodService;
    private final RestaurentService restaurentService;

    @PostMapping("/create")
    public ResponseEntity<AuthResponse<?>> createFood(@RequestBody FoodReq req) {
        try {
            var restaurant = restaurentService.findRestaurentById(req.getRestaurantId());
            return ResponseEntity.ok(foodService.createFood(req, req.getCategory(), restaurant));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AuthResponse<?>> deleteFood(@PathVariable Long id) {
        try {

            return ResponseEntity.ok(foodService.deleteFood(id));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PutMapping("/update-availability/{id}")
    public ResponseEntity<AuthResponse<?>> updateFoodAvailability(@PathVariable Long id) {
        try {

            return ResponseEntity.ok(foodService.updateAvailableStatus(id));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}