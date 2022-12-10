package com.donfood.dao;

import com.donfood.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//@Repository
public interface IAccountRepository  extends JpaRepository<Account, Long> {
    List<Account> findByEmail(String email);
    List<Account> findByFullName(String fullName);
}
