package com.food_ordering.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.model.Order;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;

    @PostMapping("/restaurant/{id}")
    public ResponseEntity<AuthResponse<?>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus) {

        try {
            List<Order> orders = orderService.getRestaurantOrders(id, orderStatus);

            var response = new AuthResponse<>("Orders history ", null, orders);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<AuthResponse<?>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam(required = true) String orderStatus) {

        try {
            Order order = orderService.updateOrder(id, orderStatus);

            var response = new AuthResponse<>("Orders history ", null, order);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}
