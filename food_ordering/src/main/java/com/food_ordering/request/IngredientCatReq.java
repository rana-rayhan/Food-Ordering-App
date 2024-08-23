package com.food_ordering.request;

import lombok.Data;

@Data
public class IngredientCatReq {
    private String name;
    private Long RestaurantId;
}
