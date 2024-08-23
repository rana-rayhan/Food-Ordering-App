package com.food_ordering;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.food_ordering.model.IngredientsCategory;
import com.food_ordering.model.IngredientsItem;
import com.food_ordering.model.Restaurant;
import com.food_ordering.repositories.IngredientCategoryRepo;
import com.food_ordering.repositories.IngredientRepo;
import com.food_ordering.services.IngredientService;
import com.food_ordering.services.RestaurentService;

class IngredientServiceTest {
    @Mock
    private IngredientRepo ingredientRepo;
    @Mock
    private IngredientCategoryRepo ingredientCatRepo;
    @Mock
    private RestaurentService restaurentService;
    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createIngredientCategory_success() throws Exception {
        // Arrange
        String name = "Category1";
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(restaurentService.findRestaurentById(restaurantId)).thenReturn(restaurant);
        when(ingredientCatRepo.save(any(IngredientsCategory.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        IngredientsCategory category = ingredientService.createIngredientCategory(name, restaurantId);

        // Assert
        assertNotNull(category);
        assertEquals(name, category.getName());
        verify(ingredientCatRepo, times(1)).save(any(IngredientsCategory.class));
    }

    @Test
    void findIngredientCategoryById_notFound() {
        // Arrange
        Long categoryId = 1L;
        when(ingredientCatRepo.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            ingredientService.findIngredientCategoryById(categoryId);
        });

        assertEquals("Ingredient not found", exception.getMessage());
    }

    @Test
    void updateStock_success() {
        // Arrange
        Long ingredientId = 1L;
        IngredientsItem item = new IngredientsItem();
        item.setId(ingredientId);
        item.setInStock(true);

        when(ingredientRepo.findById(ingredientId)).thenReturn(Optional.of(item));
        when(ingredientRepo.save(any(IngredientsItem.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        IngredientsItem updatedItem = ingredientService.updateStock(ingredientId);

        // Assert
        assertNotNull(updatedItem);
        assertFalse(updatedItem.isInStock());
        verify(ingredientRepo, times(1)).save(updatedItem);
    }
}
