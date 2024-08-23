package com.food_ordering.request;

import com.food_ordering.model.Address;

import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address address;
}
