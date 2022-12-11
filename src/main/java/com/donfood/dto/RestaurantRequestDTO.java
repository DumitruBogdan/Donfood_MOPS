package com.donfood.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@Builder
public class RestaurantRequestDTO {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private AccountRequestDTO accountRequestDTO;
    private String fiscalIdCode;
    private Integer nrPeopleHelping;
}
