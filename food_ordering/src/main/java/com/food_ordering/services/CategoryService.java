package com.food_ordering.services;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_ordering.model.Category;
import com.food_ordering.model.Restaurant;
import com.food_ordering.repositories.CategoryRepo;
import com.food_ordering.response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final RestaurentService restaurantService;
    private final CategoryRepo categoryRepo;

    public AuthResponse<?> createCategory(String name, Long userId) {
        Restaurant restaurant = restaurantService.findRestaurentById(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        Category savedCategory = categoryRepo.save(category);

        return AuthResponse.builder()
                .message("Category created!")
                .data(savedCategory)
                .build();
    }

    public AuthResponse<?> findCategoryByRestaurantId(Long id) {
        Restaurant restaurant = restaurantService.findRestaurentById(id);
        List<Category> categories = categoryRepo.findByRestaurantId(restaurant.getId());

        return AuthResponse.builder()
                .message("Categories found!")
                .data(categories)
                .build();
    }

    public AuthResponse<?> findCategory(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Category not found!"));

        return AuthResponse.builder()
                .message("Category found!")
                .data(category)
                .build();
    }
}
