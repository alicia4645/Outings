package com.example.outings.auth.domain.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(){
        super("Incorrect email or password.");
    }
    
}
