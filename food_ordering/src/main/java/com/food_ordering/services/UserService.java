package com.food_ordering.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_ordering.config.JwtService;
import com.food_ordering.model.User;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.response.AuthResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;

    public AuthResponse<?> getUserByJwtToken(String token) throws Exception {

        String email = jwtService.extractUserEmail(token);

        if (email == null) {
            throw new Exception("User email not found !");
        }
        User user = userRepo.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found !"));

        return AuthResponse.builder()
                .message("User Found !")
                .data(user)
                .build();
    }

    public AuthResponse<?> getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found !"));

        return AuthResponse.builder()
                .message("User Found !")
                .data(user)
                .build();
    }

    // Logout service
    public AuthResponse<?> logoutUser(HttpServletResponse response) {
        // Invalidate the JWT token by removing it from the user's cookies
        Cookie cookie = new Cookie("access-token", null);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Set the max age to 0 to delete the cookie

        response.addCookie(cookie);

        return AuthResponse.builder()
                .message("User logged out successfully!")
                .build();
    }
}
