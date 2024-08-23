package com.food_ordering.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.model.Category;
import com.food_ordering.model.User;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final UserRepo userRepo;

    @PostMapping("/create")
    public ResponseEntity<AuthResponse<?>> createCategory(@RequestBody Category req, Principal p) {
        try {
            User user = userRepo.findByEmail(p.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found !"));

            return ResponseEntity.ok(categoryService.createCategory(req.getName(), user.getId()));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<AuthResponse<?>> getRestaurantCategory(@PathVariable Long id) {
        try {

            return ResponseEntity.ok(categoryService.findCategoryByRestaurantId(id));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}
