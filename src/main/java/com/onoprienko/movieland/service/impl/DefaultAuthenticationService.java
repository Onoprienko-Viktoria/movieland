package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.common.request.AuthenticationRequest;
import com.onoprienko.movieland.common.request.RegisterRequest;
import com.onoprienko.movieland.common.response.AuthenticationResponse;
import com.onoprienko.movieland.entity.security.User;
import com.onoprienko.movieland.service.AuthenticationService;
import com.onoprienko.movieland.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultAuthenticationService implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = userService.saveUser(request);
        String jwtToken = jwtService.generateToken(user);
        log.info("User registered");
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userService.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        log.info("User authenticated");
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public void logout(String token) {

    }
}
