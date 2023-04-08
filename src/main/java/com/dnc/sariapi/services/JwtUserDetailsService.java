package com.dnc.sariapi.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

public interface JwtUserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, ResponseStatusException;
}
