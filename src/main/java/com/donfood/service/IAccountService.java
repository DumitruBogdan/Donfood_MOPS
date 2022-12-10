package com.donfood.service;

import com.donfood.domain.Account;
import com.donfood.dto.AccountRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
    Account register(AccountRequestDTO accountRequestDTO);
    Account login(AccountRequestDTO accountRequestDTO);
    Account update(AccountRequestDTO accountRequestDTO);
    void delete(Long id);
    Account getById(Long id);
    Account getByFullName(String fullName);

}
