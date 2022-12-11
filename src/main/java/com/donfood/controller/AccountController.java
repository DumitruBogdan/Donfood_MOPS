package com.donfood.controller;

import com.donfood.domain.Account;
import com.donfood.dto.AccountRequestDTO;
import com.donfood.dto.ONGRequestDTO;
import com.donfood.dto.ONGResponseDTO;
import com.donfood.service.AccountService;
import com.donfood.service.ONGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private ONGService ongService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/registerONG")
    public ONGResponseDTO registerONG(@RequestBody ONGRequestDTO ongRequestDTO){
        return ongService.register(ongRequestDTO);
    }
    @PostMapping("/login")
    public Account login(@RequestBody AccountRequestDTO accountRequestDTO){
        Account value = accountService.login(accountRequestDTO);
        return value;
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.delete(id);
    }

}
