package com.example.outings.auth.application.port.in;

import com.example.outings.auth.application.port.command.SignupCommand;

public interface SignupUseCase {
    void handle(SignupCommand command);
}
