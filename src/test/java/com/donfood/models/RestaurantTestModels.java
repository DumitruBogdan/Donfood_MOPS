package com.donfood.models;

import com.donfood.domain.Restaurant;
import com.donfood.dto.RestaurantRequestDTO;
import com.donfood.dto.RestaurantResponseDTO;


public class RestaurantTestModels {

    public static final Long RESTAURANT_ID = 1L;

    public static final Long INVALID_ID = 99L;

    public static RestaurantRequestDTO buildRestaurantRequestDTO() {
        return RestaurantRequestDTO.builder()
                .accountId(AccountModels.ACCOUNT_ID)
                .accountRequestDTO(AccountModels.buildAcountRequestDTO())
                .fiscalIdCode("111111")
                .nrPeopleHelping(111)
                .build();
    }

    public static Restaurant buildRestaurant() {
        return Restaurant.builder()
                .accountId(AccountModels.ACCOUNT_ID)
                .accountRest(AccountModels.buildAcount())
                .fiscalIdCode("111111")
                .nrPeopleHelping(111)
                .build();
    }

    public static RestaurantResponseDTO buildRestaurantResponseDTO() {
        return RestaurantResponseDTO.builder()
                .accountId(AccountModels.ACCOUNT_ID)
                .accountResponseDTO(AccountModels.buildAccountResponseDTO())
                .fiscalIdCode("111111")
                .nrPeopleHelping(111)
                .build();
    }
}
