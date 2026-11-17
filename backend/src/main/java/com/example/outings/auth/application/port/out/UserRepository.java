package com.example.outings.auth.application.port.out;

import java.util.Optional;

import com.example.outings.auth.domain.model.User;

public interface UserRepository{
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    User save(User user);
}
