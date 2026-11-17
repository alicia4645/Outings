package com.example.outings.auth.domain.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    
    public UserAlreadyExistsException(String email){
        super(email + " already exists.");
    }
}
