package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.common.request.AuthenticationRequest;
import com.onoprienko.movieland.common.request.RegisterRequest;
import com.onoprienko.movieland.common.response.AuthenticationResponse;
import com.onoprienko.movieland.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader String token) {
        service.logout(token);
    }

}
