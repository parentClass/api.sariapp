package com.dnc.sariapi.services;

import com.dnc.sariapi.models.request.AuthenticateRequest;
import com.dnc.sariapi.models.response.AuthenticateResponse;

public interface AuthenticateService {
    AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest);
}
