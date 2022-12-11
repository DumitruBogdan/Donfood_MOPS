package com.donfood.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantDTO {

    private Long accountId;
    private String fiscalIdCode;
    private Integer nrPeopleHelping;
}
