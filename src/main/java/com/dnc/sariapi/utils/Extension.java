package com.dnc.sariapi.utils;

import com.dnc.sariapi.repositories.MerchantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Extension {

    public static String getMerchantId(MerchantRepository merchantRepository, String accountId) {
        return merchantRepository.findByAccountId(accountId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Account from the token is not associated with a merchant")).getId();
    }
}
