package com.donfood.controller;

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

    @PostMapping("/register")
    public ONGResponseDTO registerONG(@RequestBody ONGRequestDTO ongRequestDTO){
        return ongService.register(ongRequestDTO);
    }
    @PostMapping("/login")
    public ONGResponseDTO loginONG(@RequestBody ONGRequestDTO ongRequestDTO){
        return ongService.login(ongRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.delete(id);
    }

}
