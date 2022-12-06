package com.donfood.service;

import com.donfood.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService { 
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);
    
    RestaurantDTO getRestaurantById(Long id);
    
    List<RestaurantDTO> getAllRestaurants();

    RestaurantDTO updateRestaurant( RestaurantDTO restaurantDTO);

    void deleteRestaurant(Long id);
}
