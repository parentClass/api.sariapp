package com.dnc.sariapi.services.impl;

import com.dnc.sariapi.models.dtos.AccountDTO;
import com.dnc.sariapi.models.request.AuthenticateRequest;
import com.dnc.sariapi.models.response.AuthenticateResponse;
import com.dnc.sariapi.repositories.AccountRepository;
import com.dnc.sariapi.services.AuthenticateService;
import com.dnc.sariapi.services.JwtUserDetailsService;
import com.dnc.sariapi.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.dnc.sariapi.utils.Validation.validateAuthenticationRequest;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtToken jwtToken;

    private static final String OID = "oid";

    private static final String MEMBERSHIP_TYPE = "type";

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) {
        // validate username and password
        validateAuthenticationRequest(authenticateRequest.getUsername(), authenticateRequest.getPassword());

        // retrieve account details
        AccountDTO accountDTO = accountRepository.findUserByUsername(authenticateRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not existing"));

        // validate correctness of username with password
        if(!passwordEncoder.matches(authenticateRequest.getPassword(), accountDTO.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        // only activated account is valid to be logged in
        if (Boolean.FALSE.equals(accountDTO.isActivated())) {
            throw new ResponseStatusException(HttpStatus.OK, "Account is not yet activated");
        }

        // set claims
        Map<String, Object> claims = new HashMap<>();

        claims.put(OID, accountDTO.getId());
        claims.put(MEMBERSHIP_TYPE, accountDTO.getMembershipType().name());

        String token = jwtToken.generateToken(claims, new User(accountDTO.getUsername(), accountDTO.getPassword(), new ArrayList<>()));
        Date issuedAt = jwtToken.getIssuedAtDateFromToken(token);
        Date validTil = jwtToken.getExpirationDateFromToken(token);

        return AuthenticateResponse.builder().token(token).issuedAt(issuedAt).validUntil(validTil).build();
    }
}
