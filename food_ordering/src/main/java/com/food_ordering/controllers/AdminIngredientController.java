package com.food_ordering.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.model.IngredientsCategory;
import com.food_ordering.model.IngredientsItem;
import com.food_ordering.request.IngredientCatReq;
import com.food_ordering.request.IngredientReq;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.IngredientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/ingredient")
@RequiredArgsConstructor
public class AdminIngredientController {
    private final IngredientService ingredientService;

    @PostMapping("/category/create")
    public ResponseEntity<AuthResponse<?>> createIngredientCategory(@RequestBody IngredientCatReq req) {
        try {
            IngredientsCategory category = ingredientService.createIngredientCategory(req.getName(),
                    req.getRestaurantId());

            var userResponse = AuthResponse.builder()
                    .message("Ingredient category created !")
                    .data(category)
                    .build();

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<AuthResponse<?>> createIngredient(@RequestBody IngredientReq req) {
        try {
            IngredientsItem ingredient = ingredientService.createIngredientItem(
                    req.getName(),
                    req.getRestaurentId(),
                    req.getCategoryId());

            var userResponse = AuthResponse.builder()
                    .message("Ingredient created !")
                    .data(ingredient)
                    .build();

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PutMapping("/update-stock/id")
    public ResponseEntity<AuthResponse<?>> updateStock(@PathVariable Long id) {
        try {
            IngredientsItem ingredient = ingredientService.updateStock(id);
            String message = ingredient.isInStock() ? "in stcok" : "out of stock";

            var userResponse = AuthResponse.builder()
                    .message("Ingredient : " + message)
                    .data(ingredient)
                    .build();

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/restaurant/id")
    public ResponseEntity<AuthResponse<?>> getRestaurantIngredient(@PathVariable Long id) {
        try {
            List<IngredientsItem> ingredients = ingredientService.findRestaurantsIngredients(id);

            var userResponse = AuthResponse.builder()
                    .message("Ingredient items are ! ")
                    .data(ingredients)
                    .build();

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/restaurant/category/id")
    public ResponseEntity<AuthResponse<?>> getRestaurantIngredientCategory(@PathVariable Long id) {
        try {
            List<IngredientsCategory> categories = ingredientService
                    .findIngredientCategoryByRestaurantId(id);

            var userResponse = AuthResponse.builder()
                    .message("Ingredient categories are ! ")
                    .data(categories)
                    .build();

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}