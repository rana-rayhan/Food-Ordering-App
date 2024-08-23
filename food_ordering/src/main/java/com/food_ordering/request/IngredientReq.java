package com.food_ordering.request;

import lombok.Data;

@Data
public class IngredientReq {
    private String name;
    private Long categoryId;
    private Long restaurentId;
}
