package com.food_ordering.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RestaurentDto {
    private Long id;
    private String title;
    private String decription;

    @Column(name = "image_url", length = 1000)
    private List<String> images = new ArrayList<>();
}
