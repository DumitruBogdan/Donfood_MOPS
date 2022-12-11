package com.donfood.mapper;

import com.donfood.domain.Restaurant;
import com.donfood.dto.RestaurantRequestDTO;
import com.donfood.dto.RestaurantResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapper {

    public static Restaurant requestDtoToDo(RestaurantRequestDTO restaurantRequestDTO) {
        return Restaurant.builder()
                .accountId(restaurantRequestDTO.getAccountId())
                //.accountRest(AccountMapper.requestToAccount(restaurantRequestDTO.getAccountRequestDTO()))
                .fiscalIdCode(restaurantRequestDTO.getFiscalIdCode())
                .nrPeopleHelping(restaurantRequestDTO.getNrPeopleHelping())
                .build();
    }

    public static RestaurantResponseDTO doToResponseDto(Restaurant restaurant) {
        return RestaurantResponseDTO.builder()
                .accountId(restaurant.getAccountId())
                .accountResponseDTO(AccountMapper.accountToResponse(restaurant.getAccountRest()))
                .fiscalIdCode(restaurant.getFiscalIdCode())
                .nrPeopleHelping(restaurant.getNrPeopleHelping())
//                .favOngs(restaurant.getFavOngs())
//                .donations(restaurant.getDonations())
//                .reports(restaurant.getReports())
                .build();
    }

    public static List<RestaurantResponseDTO> dosToDtos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantMapper::doToResponseDto)
                .collect(Collectors.toList());
    }
}
