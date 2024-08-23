package com.food_ordering.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginReq {
    private String email;
    private String password;
}
