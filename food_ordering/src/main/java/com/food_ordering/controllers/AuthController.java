package com.food_ordering.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.model.User;
import com.food_ordering.request.LoginReq;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse<?>> registerUser(@RequestBody User data) {
        try {
            return ResponseEntity.ok(authService.createUser(data));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse<?>> loginUser(@RequestBody LoginReq data, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(authService.loginUser(data, response));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<AuthResponse<?>> checkAuthentication(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();

            // Create response with only user details
            var response = AuthResponse.builder()
                    .message("User authenticated successfully")
                    .data(user)
                    .build();

            return ResponseEntity.ok(response);
        } else {
            // Create response for unauthenticated case
            var errorResponse = AuthResponse.builder()
                    .message("User is not authenticated")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

}
