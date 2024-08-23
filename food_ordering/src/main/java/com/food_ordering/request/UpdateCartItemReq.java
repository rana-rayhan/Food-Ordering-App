package com.food_ordering.request;

import lombok.Data;

@Data
public class UpdateCartItemReq {
    private Long cartItemId;
    private int quantity;
}
