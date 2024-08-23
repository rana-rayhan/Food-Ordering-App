package com.food_ordering.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.model.User;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.request.RestaurentReq;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.RestaurentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/restaurant")
@RequiredArgsConstructor
public class AdminRestaurentController {
    private final RestaurentService restaurentService;
    private final UserRepo userRepo;

    @PostMapping("/create")
    public ResponseEntity<AuthResponse<?>> createRestaurant(@RequestBody RestaurentReq req, Principal p) {
        try {
            User user = userRepo.findByEmail(p.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found by email !"));

            return ResponseEntity.ok(restaurentService.createRestaurent(req, user));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AuthResponse<?>> updateRestaurant(@RequestBody RestaurentReq req, @PathVariable Long id) {
        try {

            return ResponseEntity.ok(restaurentService.updateRestaurent(id, req));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AuthResponse<?>> deleteRestaurant(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restaurentService.deleteRestaurent(id));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<AuthResponse<?>> updateRestaurantStatus(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restaurentService.updateRestaurentStatus(id));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<AuthResponse<?>> findRestaurantByUserId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restaurentService.findRestaurentByUserId(id));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}
