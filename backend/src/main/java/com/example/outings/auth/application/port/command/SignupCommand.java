package com.example.outings.auth.application.port.command;

public record SignupCommand (
    String name,
    String email,
    String username,
    String password
){};
