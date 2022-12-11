package com.donfood.dto;

import com.donfood.domain.Donation;
import com.donfood.domain.ONG;
import com.donfood.domain.Report;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RestaurantResponseDTO {

    private Long accountId;
    private AccountResponseDTO accountResponseDTO;
    private String fiscalIdCode;
    private Integer nrPeopleHelping;
//    private Set<ONG> favOngs;
//    private Set<Donation> donations;
//    private Set<Report> reports;

}
