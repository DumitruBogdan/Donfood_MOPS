package com.donfood.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class AccountRequestDTO {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@NotNull
    private String email;

    //@NotNull
    private String passwordDecoded;

    private String fullName;

    private Integer accessRights;

    private Timestamp createAt;

    private Boolean accountVerified;
}
