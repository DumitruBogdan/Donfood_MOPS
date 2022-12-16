package com.donfood.dto;

import com.donfood.domain.Account;
import com.donfood.domain.Order;
import com.donfood.domain.Report;
import com.donfood.domain.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
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

    private Set<Order> orders = new HashSet<>();
}
