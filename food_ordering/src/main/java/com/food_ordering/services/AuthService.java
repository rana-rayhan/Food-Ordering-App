package com.food_ordering.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.food_ordering.config.JwtService;
import com.food_ordering.model.Cart;
import com.food_ordering.model.User;
import com.food_ordering.repositories.CartRepo;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.request.LoginReq;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.utils.CookieUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    private final CookieUtil cookieService;

    // creating a user
    public AuthResponse<?> createUser(User data) {
        Optional<User> exist = userRepo.findByEmail(data.getEmail());
        if (exist.isPresent()) {
            throw new IllegalArgumentException("Email is already exist !");
        }

        var user = new User();
        user.setFullName(data.getFullName());
        user.setEmail(data.getEmail());
        user.setRole(data.getRole());
        user.setPassword(encoder.encode(data.getPassword()));

        var savedUser = userRepo.save(user);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepo.save(cart);

        return AuthResponse.builder()
                .message("User created Successfully !")
                .data(savedUser)
                .build();
    }

    // login use
    public AuthResponse<?> loginUser(LoginReq data, HttpServletResponse response) {

        var auth = manager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));
        var user = (User) auth.getPrincipal();

        String token = jwtService.generateToken(user);
        response.addCookie(cookieService.setUserCookie(token));

        return AuthResponse.builder()
                .message("User created Successfully !")
                .token(token)
                .data(user)
                .build();
    }

}
