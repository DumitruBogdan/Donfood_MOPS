package com.donfood.dto;

import com.donfood.domain.ONG;
import com.donfood.domain.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@Builder
public class ReportRequestDTO {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long restaurantId;
    private Restaurant restaurant;
    private Long ongId;
    private ONG ong;
    private String reason;
}
