package com.onoprienko.movieland.configuration;

import com.onoprienko.movieland.entity.security.Role;
import com.onoprienko.movieland.web.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
//    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/movie/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/country/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/genre/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/review/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/movie/*").hasAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/review/*").hasAuthority(Role.USER.name())
                .anyRequest()
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
