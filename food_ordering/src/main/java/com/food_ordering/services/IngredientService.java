package com.food_ordering.services;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_ordering.model.IngredientsCategory;
import com.food_ordering.model.IngredientsItem;
import com.food_ordering.model.Restaurant;
import com.food_ordering.repositories.IngredientCategoryRepo;
import com.food_ordering.repositories.IngredientRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepo ingredientRepo;
    private final IngredientCategoryRepo ingredientCatRepo;
    private final RestaurentService restaurentService;

    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurentById = restaurentService.findRestaurentById(restaurantId);

        IngredientsCategory category = new IngredientsCategory();
        category.setRestaurant(restaurentById);
        category.setName(name);

        return ingredientCatRepo.save(category);
    }

    public IngredientsCategory findIngredientCategoryById(Long id) throws Exception {
        var categoryData = ingredientCatRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Ingredient not found"));

        return categoryData;
    }

    public List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        var restaurent = restaurentService.findRestaurentById(id);

        return ingredientCatRepo.findByRestaurantId(restaurent.getId());
    }

    public IngredientsItem createIngredientItem(String name, Long restaurantId, Long CategoryId) throws Exception {
        var restaurent = restaurentService.findRestaurentById(restaurantId);
        var category = findIngredientCategoryById(CategoryId);

        IngredientsItem ingredient = new IngredientsItem();
        ingredient.setName(name);
        ingredient.setRestaurant(restaurent);
        ingredient.setIngredientsCategory(category);

        var save = ingredientRepo.save(ingredient);
        category.getIngredientsItems().add(save);

        return save;
    }

    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        Restaurant restaurent = restaurentService.findRestaurentById(restaurantId);

        return ingredientRepo.findByRestaurantId(restaurent.getId());
    }

    public IngredientsItem updateStock(Long id) {
        var data = ingredientRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Ingredient not foudn !"));
        data.setInStock(!data.isInStock());

        return ingredientRepo.save(data);
    }
}
