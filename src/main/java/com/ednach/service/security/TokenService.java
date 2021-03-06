package com.ednach.service.security;

import org.springframework.security.core.Authentication;

/**
 * Interface for processing tokens
 */
public interface TokenService {

    String generate(Authentication authentication);

    String refresh(String token);

    String extractUsername(String token);

    boolean validate(String authToken);
}