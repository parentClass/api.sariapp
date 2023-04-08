package com.dnc.sariapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Validation {

    public static void validateAuthenticationRequest(String username, String password) {
        // check username
        if (username.isBlank() || username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username can't be left blank");
        }

        // check password
        if (password.isBlank() || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can't be left blank");
        }
    }

    public static boolean isMissing(String toValidate) {
        return toValidate == null || toValidate.isBlank() || toValidate.isEmpty();
    }
}
