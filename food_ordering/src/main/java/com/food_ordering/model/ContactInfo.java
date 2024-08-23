package com.food_ordering.model;

import lombok.Data;

// @AllArgsConstructor
// @NoArgsConstructor
// @Entity
// @Table(name = "contact_infos")

@Data
public class ContactInfo {
    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
