package com.food_ordering.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.UserService;
import com.food_ordering.utils.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CookieUtil cookieUtil;

    @GetMapping("/profile")
    public ResponseEntity<AuthResponse<?>> registerUser(HttpServletRequest request, Principal p) {
        try {
            String token = cookieUtil.getTokenFromCookies(request);

            return ResponseEntity.ok(userService.getUserByJwtToken(token.substring(7)));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<AuthResponse<?>> logoutUser(HttpServletResponse response) {
        try {
            return ResponseEntity.ok(userService.logoutUser(response));
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

}
