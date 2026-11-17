package com.example.outings.auth.application.port.command;

public record LoginCommand (
    String email,
    String password
){};
