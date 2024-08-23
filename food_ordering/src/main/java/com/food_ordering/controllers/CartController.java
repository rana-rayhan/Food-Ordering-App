package com.food_ordering.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering.model.Cart;
import com.food_ordering.model.CartItem;
import com.food_ordering.request.CartItemReq;
import com.food_ordering.request.UpdateCartItemReq;
import com.food_ordering.response.AuthResponse;
import com.food_ordering.services.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PutMapping("/cart/add")
    public ResponseEntity<AuthResponse<?>> addItemToCart(@RequestBody CartItemReq data, Principal p) {
        try {
            CartItem cartItem = cartService.addCartItem(data, p.getName());
            var response = AuthResponse.builder().message("Added to cart ").data(cartItem).build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<AuthResponse<?>> updateCartItemQuantity(@RequestBody UpdateCartItemReq data) {
        try {
            CartItem cartItem = cartService.updateCartItemQuantity(data.getCartItemId(), data.getQuantity());
            var response = AuthResponse.builder().message("Added to cart ").data(cartItem).build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @DeleteMapping("/cart-item/delete/{id}")
    public ResponseEntity<AuthResponse<?>> removeCartItem(@PathVariable Long id, Principal p) {
        try {
            Cart itemFromCart = cartService.removeItemFromCart(id, p.getName());
            var response = AuthResponse.builder().message("Removed from cart ").data(itemFromCart).build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<AuthResponse<?>> clearCart(Principal p) {
        try {
            // var response = AuthResponse.builder().message("Clear cart
            // ").data(cart).build();
            Cart cart = cartService.clearCart(p.getName());
            var response = new AuthResponse<>("Clear cart ", null, cart);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }

    @GetMapping("/cart/user")
    public ResponseEntity<AuthResponse<?>> findUserCart(Principal p) {
        try {
            // var response = AuthResponse.builder().message("Clear cart
            // ").data(cart).build();
            Cart cart = cartService.findCartByUserId(p.getName());
            var response = new AuthResponse<>("User cart ", null, cart);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var errorResponse = AuthResponse.builder().message("Error: " + e.getMessage()).build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
}