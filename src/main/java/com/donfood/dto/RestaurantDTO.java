package com.donfood.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantDTO {

    private Long accountId;
    private String fiscalIdCode;
    private Integer nrPeopleHelping;
}
