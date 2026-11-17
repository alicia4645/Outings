package com.example.outings.auth.application.port.in;

import com.example.outings.auth.application.port.command.LoginCommand;
import com.example.outings.auth.application.port.command.LoginResult;

public interface LoginUseCase {
    LoginResult handle(LoginCommand command);
}
