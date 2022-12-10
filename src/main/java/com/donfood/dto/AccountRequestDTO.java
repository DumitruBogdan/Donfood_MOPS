package com.donfood.dto;

import com.donfood.domain.ONG;
import com.donfood.domain.Restaurant;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
public class AccountRequestDTO {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ONG ong;

    private Restaurant restaurant;

    @NotNull
    private String email;

    @NotNull
    private String passwordDecoded;

    private String fullName;

    private Integer accessRights;

    private Timestamp createAt;

    private Boolean accountVerified;
}
