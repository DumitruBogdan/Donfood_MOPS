package com.donfood.dto;

import com.donfood.domain.Account;
import com.donfood.domain.Report;
import com.donfood.domain.Restaurant;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Data
@Builder
public class ONGResponseDTO {

    @NotNull
    private Long accountId;

    private Account accountONG;

    @NotNull
    private String address;

    private Double socialScore;

    private Set<Restaurant> favRestaurants = new HashSet<>();

    private Set<Report> reports = new HashSet<>();

    private Set<Report> orders = new HashSet<>();
}
