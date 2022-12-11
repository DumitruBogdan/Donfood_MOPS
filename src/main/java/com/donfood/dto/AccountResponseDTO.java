package com.donfood.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Builder
@Setter
@Getter
public class AccountResponseDTO {

    private Long id;

    private String email;

    private String fullName;

    private Integer accessRights;

    private Timestamp createAt;

    private Boolean accountVerified;
}
