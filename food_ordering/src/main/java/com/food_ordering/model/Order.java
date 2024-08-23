package com.food_ordering.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long totalAmount;
    private Long totalPrice;
    private int totalItem;
    private String orderStatus;
    private Date createdAt;

    @ManyToOne
    private User customer;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private Address deliveryAddress;

    @OneToMany
    private List<OrderItem> items = new ArrayList<>();

    // private Payment payment;
}
