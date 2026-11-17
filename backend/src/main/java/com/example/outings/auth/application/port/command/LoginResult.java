package com.example.outings.auth.application.port.command;

import java.util.Set;

import com.example.outings.auth.domain.model.Role;

public record LoginResult(
    String email,
    Set<Role> roles
){}
