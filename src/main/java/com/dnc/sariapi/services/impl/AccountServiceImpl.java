package com.dnc.sariapi.services.impl;

import com.dnc.sariapi.models.dtos.AccountDTO;
import com.dnc.sariapi.repositories.AccountRepository;
import com.dnc.sariapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        // encode password
        accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));

        return accountRepository.save(accountDTO);
    }
}