package com.donfood.service;

import com.donfood.dto.RestaurantRequestDTO;
import com.donfood.dto.RestaurantResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService { 
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO restaurantRequestDTO);

    RestaurantResponseDTO getRestaurantById(Long id);
    
    List<RestaurantResponseDTO> getAllRestaurants();

    RestaurantResponseDTO updateRestaurant(RestaurantRequestDTO restaurantRequestDTO);

    void deleteRestaurant(Long id);
}
