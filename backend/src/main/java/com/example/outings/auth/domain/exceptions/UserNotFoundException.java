package com.example.outings.auth.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String email){
        super(email + " not found.");
    }
}
