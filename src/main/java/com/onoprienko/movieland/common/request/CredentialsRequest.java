package com.onoprienko.movieland.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CredentialsRequest {
    private String email;
    private String password;
}
