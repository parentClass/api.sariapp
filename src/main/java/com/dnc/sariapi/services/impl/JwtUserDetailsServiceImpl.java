package com.dnc.sariapi.services.impl;

import com.dnc.sariapi.models.dtos.AccountDTO;
import com.dnc.sariapi.repositories.AccountRepository;
import com.dnc.sariapi.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AccountDTO accountDTO = accountRepository.findUserByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found"));

        // only activated account is valid to be logged in
        if (Boolean.FALSE.equals(accountDTO.isActivated())) {
            throw new ResponseStatusException(HttpStatus.OK, "Account is not yet activated");
        }

        return new User(accountDTO.getUsername(), accountDTO.getPassword(), new ArrayList<>());
    }
}