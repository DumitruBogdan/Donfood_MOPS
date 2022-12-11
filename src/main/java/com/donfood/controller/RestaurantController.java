package com.donfood.controller;

import com.donfood.dto.RestaurantRequestDTO;
import com.donfood.dto.RestaurantResponseDTO;
import com.donfood.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/register")
    public RestaurantResponseDTO registerRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO){
        return restaurantService.createRestaurant(restaurantRequestDTO);
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getRestaurant(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @PutMapping("/{id}")
    public RestaurantResponseDTO updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        restaurantRequestDTO.setAccountId(id);
        return restaurantService.updateRestaurant(restaurantRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}