package com.onoprienko.movieland.service;

import com.onoprienko.movieland.common.request.AuthenticationRequest;
import com.onoprienko.movieland.common.request.RegisterRequest;
import com.onoprienko.movieland.common.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(String token);
}
