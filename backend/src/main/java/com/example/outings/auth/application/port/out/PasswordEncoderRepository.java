package com.example.outings.auth.application.port.out;

public interface PasswordEncoderRepository {
    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword); 
}
