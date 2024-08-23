package com.food_ordering.request;

import java.util.List;

import lombok.Data;

@Data
public class CartItemReq {
    private Long foodId;
    private int quantity;
    private List<String> ingredients;
}
