package com.food_ordering.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.model.Order;
import com.food_ordering.model.User;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.request.OrderRequest;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserRepo userRepo;

    @PostMapping("/create")
    public ResponseEntity<AuthResponse<?>> createOrder(@RequestBody OrderRequest data, Principal p) {
        try {
            Order order = orderService.createOrder(data, p.getName());
            var response = new AuthResponse<>("Order created ", null, order);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<AuthResponse<?>> getOrderHistory(@RequestBody OrderRequest data, Principal p) {
        try {
            User user = userRepo.findByEmail(p.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            List<Order> order = orderService.getUserOrders(user.getId());

            var response = new AuthResponse<>("Order created ", null, order);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}
