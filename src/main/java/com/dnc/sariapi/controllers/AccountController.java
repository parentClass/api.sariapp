package com.dnc.sariapi.controllers;

import com.dnc.sariapi.models.dtos.AccountDTO;
import com.dnc.sariapi.models.response.SariBaseResponse;
import com.dnc.sariapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("account")
class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<SariBaseResponse> create(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(accountService.create(accountDTO))
                        .build()
        );
    }
}
