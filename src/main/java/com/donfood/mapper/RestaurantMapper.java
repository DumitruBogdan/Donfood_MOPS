package com.donfood.mapper;

import com.donfood.domain.Restaurant;
import com.donfood.dto.RestaurantDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapper {

    public static Restaurant dtoToDo(RestaurantDTO restaurantDTO) {
        return Restaurant.builder()
                .accountId(restaurantDTO.getAccountId())
                .fiscalIdCode(restaurantDTO.getFiscalIdCode())
                .nrPeopleHelping(restaurantDTO.getNrPeopleHelping())
                .build();
    }

    public static RestaurantDTO doToDto(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .accountId(restaurant.getAccountId())
                .fiscalIdCode(restaurant.getFiscalIdCode())
                .nrPeopleHelping(restaurant.getNrPeopleHelping())
                .build();
    }

    public static List<RestaurantDTO> dosToDtos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantMapper::doToDto)
                .collect(Collectors.toList());
    }
}
