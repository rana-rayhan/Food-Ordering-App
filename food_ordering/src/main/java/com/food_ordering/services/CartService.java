package com.food_ordering.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_ordering.model.Cart;
import com.food_ordering.model.CartItem;
import com.food_ordering.model.User;
import com.food_ordering.repositories.CartItemRepo;
import com.food_ordering.repositories.CartRepo;
import com.food_ordering.repositories.FoodRepo;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.request.CartItemReq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final UserRepo userRepo;
    private final FoodRepo foodRepo;

    public CartItem addCartItem(CartItemReq req, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var food = foodRepo.findById(req.getFoodId())
                .orElseThrow(() -> new UsernameNotFoundException("Food item is Empty !"));

        var cart = cartRepo.findCartByCustomerId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Cart item is Empty !"));

        for (CartItem item : cart.getCartItems()) {
            if (item.getFood().equals(food)) {
                int quantity = item.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(item.getId(), quantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());
        CartItem savedCartItem = cartItemRepo.save(newCartItem);
        cart.getCartItems().add(savedCartItem);

        return savedCartItem;
    }

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
        var cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new UsernameNotFoundException("Cart item is Empty !"));
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getTotalPrice() * quantity);

        return cartItemRepo.save(cartItem);
    }

    public Cart removeItemFromCart(Long cartItemId, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var cart = cartRepo.findCartByCustomerId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("cart not found"));
        var cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new UsernameNotFoundException("CartItem not found"));
        cart.getCartItems().remove(cartItem);

        return cartRepo.save(cart);
    }

    public Long calculateCartItemTotal(Cart cart) {
        Long total = 0L;
        for (CartItem item : cart.getCartItems()) {
            total += item.getFood().getPrice() * item.getQuantity();
        }
        return total;
    }

    public Cart findCartById(Long cartId) {
        var cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new UsernameNotFoundException("cart not found"));
        return cart;
    }

    public Cart findCartByUserId(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var cart = cartRepo.findCartByCustomerId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("cart not found"));

        return cart;
    }

    public Cart clearCart(String userEmail) {
        Cart cart = findCartByUserId(userEmail);
        cart.getCartItems().clear();

        return cartRepo.save(cart);
    }
}
