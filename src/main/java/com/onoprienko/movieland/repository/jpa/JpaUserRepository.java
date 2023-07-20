package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
