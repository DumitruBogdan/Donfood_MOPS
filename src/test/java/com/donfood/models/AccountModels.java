package com.donfood.models;

import com.donfood.domain.Account;
import com.donfood.dto.AccountRequestDTO;
import com.donfood.dto.AccountResponseDTO;

import java.sql.Timestamp;
import java.time.Instant;

public class AccountModels {

    public static final Long ACCOUNT_ID = 1L;

    public static final Long INVALID_ID = 99L;

    public static AccountRequestDTO buildAcountRequestDTO() {
        return AccountRequestDTO.builder()
                .id(ACCOUNT_ID)
                .email("email_test@email.com")
                .passwordDecoded("parola1")
                .fullName("Test number 1")
                .accessRights(1)
                .createAt(Timestamp.valueOf("2018-09-01 09:01:15"))
                .accountVerified(true)
                .build();
    }

    public static Account buildAcount() {
        return Account.builder()
                .id(ACCOUNT_ID)
                .email("email_test@email.com")
                .passwordEncoded("as234asdR5af")
                .fullName("Test number 1")
                .accessRights(1)
                .createAt(Timestamp.from(Instant.now()))
                .accountVerified(true)
                .build();
    }

    public static AccountResponseDTO buildAccountResponseDTO() {
        return AccountResponseDTO.builder().id(ACCOUNT_ID)
                .email("email_test@email.com")
                .fullName("Test number 1")
                .accessRights(1)
                .createAt(Timestamp.from(Instant.now()))
                .accountVerified(true)
                .build();
    }
}
