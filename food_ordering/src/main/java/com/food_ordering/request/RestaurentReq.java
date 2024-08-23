package com.food_ordering.request;

import java.util.List;

import com.food_ordering.model.Address;
import com.food_ordering.model.ContactInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurentReq {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfo contactInformation;
    private String opningHours;
    private List<String> images;

}
