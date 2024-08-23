package com.food_ordering.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_ordering.model.Address;
import com.food_ordering.model.CartItem;
import com.food_ordering.model.Order;
import com.food_ordering.model.OrderItem;
import com.food_ordering.model.User;
import com.food_ordering.repositories.AddressRepo;
import com.food_ordering.repositories.OrderItemRepo;
import com.food_ordering.repositories.OrderRepo;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.request.OrderRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final RestaurentService restaurentService;
    private final CartService cartService;

    public Order createOrder(OrderRequest req, String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Address address = req.getAddress();
        Address savedAddress = addressRepo.save(address);

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepo.save(user);
        }

        var restaurant = restaurentService.findRestaurentById(req.getRestaurantId());
        Order order = new Order();
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(savedAddress);
        order.setCreatedAt(new Date());
        order.setOrderStatus("PENDING");

        var cart = cartService.findCartByUserId(user.getEmail());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepo.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long total = cartService.calculateCartItemTotal(cart);
        order.setItems(orderItems);
        order.setTotalPrice(total);
        var savedOrder = orderRepo.save(order);
        restaurant.getOrders().add(savedOrder);

        return order;
    }

    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);

        if (orderStatus.equals("OUT_FOR_DELIVERY") ||
                orderStatus.equals("DELIVERED") ||
                orderStatus.equals("COMPLETED") ||
                orderStatus.equals("PENDING")) {

            order.setOrderStatus(orderStatus);
            return orderRepo.save(order);
        } else {
            throw new Exception("Please select a valid order status");
        }
    }

    public void cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);
        orderRepo.delete(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepo.findByCustomerId(userId);
    }

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) {
        List<Order> orders = orderRepo.findByRestaurantId(restaurantId);

        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus))
                    .collect(Collectors.toList());
        }

        return orders;
    }

    private Order findOrderById(Long orderId) {
        var order = orderRepo.findById(orderId)
                .orElseThrow(() -> new UsernameNotFoundException("Order not found"));

        return order;
    }
}
