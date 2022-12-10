package com.donfood.dto;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ONGRequestDTO {
    //@NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    private AccountRequestDTO accountRequestONG;

    private String address;

    private Double socialScore;
}
