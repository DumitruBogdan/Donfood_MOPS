package com.donfood.mapper;

import com.donfood.domain.Donation;
import com.donfood.dto.DonationRequestDTO;

public class DonationMapper {
    public static Donation requestToDonation(DonationRequestDTO donationRequestDTO){
        Donation donation = Donation.builder()
                .restaurantId(donationRequestDTO.getRestaurantId())
                .restaurant(donationRequestDTO.getRestaurant())
                .expirationDate(donationRequestDTO.getExpirationDate())
                .quantity(donationRequestDTO.getQuantity())
                .quantityMeasure(donationRequestDTO.getQuantityMeasure())
                .product(donationRequestDTO.getProduct())
                .pickUpLocation(donationRequestDTO.getPickUpLocation())
                .pickUpTime(donationRequestDTO.getPickUpTime())
                .photo(donationRequestDTO.getPhoto())
                .createdAt(donationRequestDTO.getCreatedAt())
                .modifiedAt(donationRequestDTO.getModifiedAt())
                .build();
        return donation;
    }
}
