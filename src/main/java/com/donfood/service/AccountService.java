package com.donfood.service;

import com.donfood.dao.IAccountRepository;
import com.donfood.domain.Account;
import com.donfood.dto.AccountRequestDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Service
public class AccountService implements  IAccountService{
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Account register(AccountRequestDTO accountRequestDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        accountRequestDTO.setPasswordDecoded(bCryptPasswordEncoder.encode(accountRequestDTO.getPasswordDecoded()));
        accountRequestDTO.setAccountVerified(false);
        accountRequestDTO.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRequestDTO.setAccessRights(1);
        Account account = AccountMapper.requestToAccount(accountRequestDTO);
        //return accountRepository.save(account);
        return account;
    }

    @Override
    public Account login(AccountRequestDTO accountRequestDTO) {
        Optional<Account> dbAccount = accountRepository.findByEmail(accountRequestDTO.getEmail()).stream().findFirst();
        if(dbAccount.equals(Optional.empty()))
            throw new ResourceNotFoundException("Email or password incorrect");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean passwordIsValid = bCryptPasswordEncoder.matches(accountRequestDTO.getPasswordDecoded(), dbAccount.get().getPasswordEncoded());
        if(!passwordIsValid)
            throw new ResourceNotFoundException("Email or password incorrect");
        else
            return dbAccount.get();
    }

    @Override
    public Account update(AccountRequestDTO accountRequestDTO) {
        Optional<Account> dbAccount = accountRepository.findById(accountRequestDTO.getId()).stream().findFirst();
        if(dbAccount.equals(Optional.empty()))
            throw new ResourceNotFoundException("Account was not found by id");
        // email is not editable

        Account account = AccountMapper.requestToAccount(accountRequestDTO);
        accountRepository.save(account);
        return account;
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
        if(dbAccount.equals(Optional.empty()))
            throw new ResourceNotFoundException("Account was not found by id");
        return dbAccount.get();
    }

    @Override
    public Account getByFullName(String fullName) {
        if (fullName.equals(""))
            throw new IllegalArgumentException("The name is empty");
        Optional<Account> dbAccount = accountRepository.findByFullName(fullName).stream().findFirst();
        if(dbAccount.equals(Optional.empty()))
            throw new ResourceNotFoundException("Account was not found by name");
        return dbAccount.get();
    }

}
