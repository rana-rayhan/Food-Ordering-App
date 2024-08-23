package com.food_ordering.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_ordering.dto.RestaurentDto;
import com.food_ordering.model.Address;
import com.food_ordering.model.Restaurant;
import com.food_ordering.model.User;
import com.food_ordering.repositories.AddressRepo;
import com.food_ordering.repositories.RestaurentRepo;
import com.food_ordering.repositories.UserRepo;
import com.food_ordering.request.RestaurentReq;
import com.food_ordering.response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurentService {

    private final RestaurentRepo restaurentRepo;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public AuthResponse<?> createRestaurent(RestaurentReq req, User user) {

        Address address = addressRepo.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInfo(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpningHours(req.getOpningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        var saveRestaurent = restaurentRepo.save(restaurant);

        return AuthResponse.builder()
                .message("Retaurent is created successfully !")
                .data(saveRestaurent)
                .build();
    }

    public AuthResponse<?> updateRestaurent(Long id, RestaurentReq data) {
        Restaurant restaurant = findRestaurentById(id);

        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(data.getCuisineType());
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(data.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(data.getName());
        }
        var saveRestaurent = restaurentRepo.save(restaurant);

        return AuthResponse.builder()
                .message("Retaurent is updated successfully !")
                .data(saveRestaurent)
                .build();
    }

    public AuthResponse<?> deleteRestaurent(Long id) {
        var res = restaurentRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Restaurent not found by id !"));
        restaurentRepo.delete(res);

        return AuthResponse.builder()
                .message("Retaurent is deleted successfully !")
                .build();
    }

    public AuthResponse<?> getAllRestaurent() {
        List<Restaurant> restaurantList = restaurentRepo.findAll();

        return AuthResponse.builder()
                .message("All Retaurent is fetched successfully !")
                .data(restaurantList)
                .build();
    }

    public AuthResponse<?> searchRestaurent(String keywords) {
        String word = keywords.toLowerCase();
        List<Restaurant> bySearchQuery = restaurentRepo.findBySearchQuery(word);

        return AuthResponse.builder()
                .message("Restaurants are found successfully!")
                .data(bySearchQuery)
                .build();
    }

    public Restaurant findRestaurentById(Long id) {
        var restaurent = restaurentRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Restaurent not found by restaurent id !"));
        return restaurent;
    }

    public AuthResponse<?> findRestaurentByUserId(Long id) {
        var restaurent = restaurentRepo.findByOwnerId(id)
                .orElseThrow(() -> new UsernameNotFoundException("Restaurent not found by owner id !"));

        return AuthResponse.builder()
                .message("Restaurants is found successfully!")
                .data(restaurent)
                .build();
    }

    public AuthResponse<?> addToFavorite(Long restaurentId, User user) {
        Restaurant restaurant = findRestaurentById(restaurentId);
        RestaurentDto dto = new RestaurentDto();

        dto.setDecription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurentId);

        String message;
        if (user.getFavorites().contains(dto)) {
            user.getFavorites().remove(dto);
            message = "Restaurant has been removed from your favorites list!";
        } else {
            user.getFavorites().add(dto);
            message = "Restaurant has been added to your favorites list!";
        }
        userRepo.save(user);

        return AuthResponse.builder()
                .message(message)
                .data(dto)
                .build();
    }

    public AuthResponse<?> updateRestaurentStatus(Long restaurentId) {
        Restaurant restaurant = findRestaurentById(restaurentId);
        restaurant.setOpen(!restaurant.isOpen());
        var save = restaurentRepo.save(restaurant);

        String message = restaurant.isOpen() ? "Restaurant is now open!" : "Restaurant is now closed!";

        return AuthResponse.builder()
                .message(message)
                .data(save.getName())
                .build();
    }
}
