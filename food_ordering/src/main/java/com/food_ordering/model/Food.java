package com.food_ordering.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long price;

    private String name;
    private String description;

    private boolean available;
    private boolean isVegetarian;
    private boolean isSeasonal;

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;

    private Date creationDate;

    @ManyToOne
    private Category foodCategory;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToMany
    private List<IngredientsItem> ingredients = new ArrayList<>();
}
