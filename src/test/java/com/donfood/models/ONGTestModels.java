package com.donfood.models;

import com.donfood.domain.ONG;
import com.donfood.dto.ONGRequestDTO;
import com.donfood.dto.ONGResponseDTO;

public class ONGTestModels {

    public static final Long ONG_ID = 1L;

    public static final Long INVALID_ID = 99L;

    public static ONGRequestDTO buildONGRequestDTO() {
        return ONGRequestDTO.builder()
                .accountId(AccountModels.ACCOUNT_ID)
                .accountRequestDTO(AccountModels.buildAcountRequestDTO())
                .address("Address 1")
                .socialScore(111.0)
                .build();
    }

    public static ONG buildONG() {
        return ONG.builder()
                .accountId(AccountModels.ACCOUNT_ID)
                .accountONG(AccountModels.buildAcount())
                .address("Address 1")
                .socialScore(111.0)
                .build();
    }

    public static ONGResponseDTO buildONGResponseDTO() {
        return ONGResponseDTO.builder()
                .accountId(AccountModels.ACCOUNT_ID)
                .accountONG(AccountModels.buildAccountResponseDTO())
                .address("Address 1")
                .socialScore(111.0)
                .build();
    }
}
