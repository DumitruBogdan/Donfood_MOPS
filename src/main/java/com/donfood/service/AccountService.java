package com.donfood.service;

import com.donfood.dao.IAccountRepository;
import com.donfood.domain.Account;
import com.donfood.domain.Restaurant;
import com.donfood.dto.AccountRequestDTO;
import com.donfood.dto.RestaurantRequestDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Account register(AccountRequestDTO accountRequestDTO) {
        if(accountRequestDTO.getEmail() == null || accountRequestDTO.getPasswordDecoded() == null)
            throw new IllegalArgumentException("Email and password cannot be null");
        Optional <Account> accountTaken = accountRepository.findByEmail(accountRequestDTO.getEmail()).stream().findFirst();
        if(!accountTaken.equals(Optional.empty()))
            throw new IllegalArgumentException("Email is taken.");
        accountRequestDTO.setPasswordDecoded(accountRequestDTO.getPasswordDecoded());
        accountRequestDTO.setAccountVerified(false);
        accountRequestDTO.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRequestDTO.setAccessRights(1);
        return accountRepository.save(AccountMapper.requestToAccount(accountRequestDTO));
    }

    @Override
    public Account login(AccountRequestDTO accountRequestDTO) {
        if(accountRequestDTO.getEmail() == null || accountRequestDTO.getPasswordDecoded() == null)
            throw new IllegalArgumentException("Email and password cannot be null");

        Optional<Account> dbAccount = accountRepository.findByEmail(accountRequestDTO.getEmail()).stream().findFirst();
        if (dbAccount.equals(Optional.empty()))
            throw new ResourceNotFoundException("Email or password incorrect");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean passwordIsValid = bCryptPasswordEncoder.matches(accountRequestDTO.getPasswordDecoded(), dbAccount.get().getPasswordEncoded());
        if (!passwordIsValid)
            throw new ResourceNotFoundException("Email or password incorrect");
        else
            return dbAccount.get();
    }

    @Override
    public Account update(Long id, AccountRequestDTO accountRequestDTO) {
        Optional<Account> dbAccount = accountRepository.findById(id).stream().findFirst();
        if (dbAccount.equals(Optional.empty()))
            throw new ResourceNotFoundException("Account was not found by id");
        // email is not editable

        if(accountRequestDTO.getPasswordDecoded() != null){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            dbAccount.get().setPasswordEncoded(bCryptPasswordEncoder.encode(accountRequestDTO.getPasswordDecoded()));
        }
        if(accountRequestDTO.getFullName() != null)
            dbAccount.get().setFullName(accountRequestDTO.getFullName());

        if(accountRequestDTO.getAccessRights() != null)
            dbAccount.get().setAccessRights(accountRequestDTO.getAccessRights());

        if(accountRequestDTO.getCreateAt() != null)
            dbAccount.get().setCreateAt(accountRequestDTO.getCreateAt());

        if(accountRequestDTO.getAccountVerified() != null)
            dbAccount.get().setAccountVerified(accountRequestDTO.getAccountVerified());

        return dbAccount.get();
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!accountRepository.existsById(id))
            throw new ResourceNotFoundException("The account with id: " + id + " was not found");
        accountRepository.deleteById(id);
    }

    @Override
    public Account getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        Optional<Account> dbAccount = accountRepository.findById(id).stream().findFirst();
        if (dbAccount.equals(Optional.empty()))
            throw new ResourceNotFoundException("Account was not found by id");
        return dbAccount.get();
    }

    @Override
    public Account getByFullName(String fullName) {
        if (fullName.equals(""))
            throw new IllegalArgumentException("The name is empty");
        Optional<Account> dbAccount = accountRepository.findByFullName(fullName).stream().findFirst();
        if (dbAccount.equals(Optional.empty()))
            throw new ResourceNotFoundException("Account was not found by name");
        return dbAccount.get();
    }

}
