package com.onoprienko.movieland.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails user);

    String generateToken(UserDetails userDetails);
}
