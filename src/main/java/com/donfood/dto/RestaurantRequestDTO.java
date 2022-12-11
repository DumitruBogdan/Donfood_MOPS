package com.donfood.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantRequestDTO {

    private Long accountId;
    private AccountRequestDTO accountRequestDTO;
    private String fiscalIdCode;
    private Integer nrPeopleHelping;
}
