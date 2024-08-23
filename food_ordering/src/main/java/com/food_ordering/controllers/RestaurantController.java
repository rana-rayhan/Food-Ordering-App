package com.food_ordering.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.model.Restaurant;
import com.food_ordering.model.User;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.RestaurentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurentService restaurentService;
    private final UserRepo userRepo;

    @GetMapping
    public ResponseEntity<AuthResponse<?>> searchRestaurant() {
        try {
            return ResponseEntity.ok(restaurentService.getAllRestaurent());
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthResponse<?>> searchRestaurantById(@PathVariable Long id) {
        try {
            Restaurant restaurentById = restaurentService.findRestaurentById(id);

            var response = AuthResponse.builder()
                    .message("Restaurant found !")
                    .data(restaurentById)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<AuthResponse<?>> searchRestaurant(@RequestParam String keywords) {
        try {
            return ResponseEntity.ok(restaurentService.searchRestaurent(keywords));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/add-favorite/{id}")
    public ResponseEntity<AuthResponse<?>> addToFavorite(@PathVariable Long id, Principal p) {
        try {
            User user = userRepo.findByEmail(p.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found by email !"));

            return ResponseEntity.ok(restaurentService.addToFavorite(id, user));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}
