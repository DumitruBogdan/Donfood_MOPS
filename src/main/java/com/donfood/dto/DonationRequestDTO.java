package com.donfood.dto;

import com.donfood.domain.Restaurant;
import com.donfood.domain.enums.Measure;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class DonationRequestDTO {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long restaurantId;

    private Restaurant restaurant;

    private Timestamp expirationDate;

    private Double quantity;

    private Measure quantityMeasure;

    private String product;

    private String pickUpLocation;

    private Time pickUpTime;

    private String photo;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

}
