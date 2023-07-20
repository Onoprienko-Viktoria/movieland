package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.common.request.RegisterRequest;
import com.onoprienko.movieland.entity.security.Role;
import com.onoprienko.movieland.entity.security.User;
import com.onoprienko.movieland.repository.jpa.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return jpaUserRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    public User saveUser(RegisterRequest request) {
        jpaUserRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new RuntimeException("User with email " + request.getEmail() + "already exist");
        });
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();
        return jpaUserRepository.save(user);
    }

    public User findByEmail(String username) {
        return jpaUserRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }
}
