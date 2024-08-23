package com.food_ordering.request;

import java.util.List;

import com.food_ordering.model.Category;
import com.food_ordering.model.IngredientsItem;

import lombok.Data;

@Data
public class FoodReq {

    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarin;
    private boolean seasional;
    private List<IngredientsItem> ingredientsItems;

}
