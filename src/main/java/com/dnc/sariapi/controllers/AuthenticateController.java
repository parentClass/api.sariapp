package com.dnc.sariapi.controllers;

import com.dnc.sariapi.models.request.AuthenticateRequest;
import com.dnc.sariapi.models.response.SariBaseResponse;
import com.dnc.sariapi.services.AuthenticateService;
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
@RequestMapping("authenticate")
public class AuthenticateController {

    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping
    public ResponseEntity<SariBaseResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        return ResponseEntity.ok(
                SariBaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .body(authenticateService.authenticate(authenticateRequest))
                        .build());
    }
}
