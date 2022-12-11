package com.donfood.mapper;

import com.donfood.domain.Account;
import com.donfood.domain.ONG;
import com.donfood.dto.AccountRequestDTO;
import com.donfood.dto.AccountResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AccountMapper {
    public static Account requestToAccount(AccountRequestDTO accountRequestDTO) {
        Account account = Account.builder()
                .email(accountRequestDTO.getEmail())
                .passwordEncoded(accountRequestDTO.getPasswordDecoded()) //to avoid an error
                .fullName(accountRequestDTO.getFullName())
                .accessRights(accountRequestDTO.getAccessRights())
                .createAt(accountRequestDTO.getCreateAt())
                .accountVerified(accountRequestDTO.getAccountVerified())
                .build();
        if (accountRequestDTO.getId() != null)
            account.setId(accountRequestDTO.getId());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        account.setPasswordEncoded(bCryptPasswordEncoder.encode(accountRequestDTO.getPasswordDecoded()));
        return account;
    }

    public static AccountResponseDTO accountToResponse(Account account) {

        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .accessRights(account.getAccessRights())
                .createAt(account.getCreateAt())
                .accountVerified(account.getAccountVerified())
                .build();
    }

    public ONG requestToONG(AccountRequestDTO accountRequestDTO){
        ONG ong = ONG.builder()
                .accountId(accountRequestDTO.getId())
                .build();
        ong.setAccountONG(this.requestToAccount(accountRequestDTO));
        return ong;
    }
}
