package com.donfood.dto;

import com.donfood.domain.Donation;
import com.donfood.domain.ONG;
import com.donfood.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class OrderRequestDTO {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long donationId;

    private Donation donation;

    private Long ongId;

    private ONG ong;

    private Double quantitySelected;

    private Status status;

    private Timestamp createdAt;
}
