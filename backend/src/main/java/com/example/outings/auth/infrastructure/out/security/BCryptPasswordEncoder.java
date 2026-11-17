package com.example.outings.auth.infrastructure.out.security;

import com.example.outings.auth.application.port.out.PasswordEncoderRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component 
public class BCryptPasswordEncoder implements PasswordEncoderRepository {
    
    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
