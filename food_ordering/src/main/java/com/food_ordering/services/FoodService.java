package com.food_ordering.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_ordering.model.Category;
import com.food_ordering.model.Food;
import com.food_ordering.model.Restaurant;
import com.food_ordering.repositories.FoodRepo;
import com.food_ordering.request.FoodReq;
import com.food_ordering.response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepo foodRepo;

    public AuthResponse<?> createFood(FoodReq req, Category category, Restaurant restaurant) {
        // Create a new Food object
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredientsItems());
        food.setSeasonal(req.isSeasional());
        food.setVegetarian(req.isVegetarin());

        Food save = foodRepo.save(food);
        restaurant.getFoods().add(save);

        return AuthResponse.builder()
                .message("Food item created !")
                .data(save)
                .build();
    }

    public AuthResponse<?> deleteFood(Long foodId) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new UsernameNotFoundException("Food not foudn !"));
        food.setRestaurant(null);
        foodRepo.delete(food);

        return AuthResponse.builder()
                .message("Food item deleted !")
                .build();

    }

    public AuthResponse<?> getRestaurantFoods(Long restaurantId,
            boolean isVeg,
            boolean isNonVeg,
            boolean isSeasonal,
            String foodCategory) {

        List<Food> foods = foodRepo.findByRestaurantId(restaurantId);

        if (isVeg) {
            foods = filetrByVeg(foods, isVeg);
        }
        if (isNonVeg) {
            foods = filetrByNonVeg(foods, isNonVeg);
        }

        if (isSeasonal) {
            foods = filetrBySeasonal(foods, isNonVeg);
        }

        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }

        return AuthResponse.builder()
                .message("Food items are found !")
                .data(foods)
                .build();
    }

    public AuthResponse<?> searchFood(String keyword) {
        List<Food> bySearchQuery = foodRepo.findBySearchQuery(keyword);

        return AuthResponse.builder()
                .message("Food items are found !")
                .data(bySearchQuery)
                .build();
    }

    public AuthResponse<?> findFoodById(Long foodId) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new UsernameNotFoundException("Food not found !"));

        return AuthResponse.builder()
                .message("Food item is found !")
                .data(food)
                .build();
    }

    public AuthResponse<?> updateAvailableStatus(Long foodId) {
        Food food = foodRepo.findById(foodId).orElseThrow(() -> new UsernameNotFoundException("Food not found !"));

        food.setAvailable(!food.isAvailable());
        var save = foodRepo.save(food);

        String message = food.isAvailable() ? "Food is now available!" : "Food is not available!";

        return AuthResponse.builder()
                .message(message)
                .data(save.getName())
                .build();
    }

    // util method ---***
    private List<Food> filetrByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> food.isVegetarian() == !isNonVeg).collect(Collectors.toList());

    }

    private List<Food> filetrByVeg(List<Food> foods, boolean isVeg) {
        return foods.stream().filter(food -> food.isVegetarian() == isVeg).collect(Collectors.toList());
    }

    private List<Food> filetrBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isVegetarian() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {

        return foods.stream()
                .filter(food -> {
                    if (food.getFoodCategory() != null) {
                        return food.getFoodCategory().getName().equals(foodCategory);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}
