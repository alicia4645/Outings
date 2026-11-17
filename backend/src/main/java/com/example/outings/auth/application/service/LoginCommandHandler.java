package com.example.outings.auth.application.service;

import java.util.Set;
import org.springframework.stereotype.Service;

import com.example.outings.auth.application.port.command.LoginCommand;
import com.example.outings.auth.application.port.command.LoginResult;
import com.example.outings.auth.application.port.in.LoginUseCase;
import com.example.outings.auth.application.port.out.PasswordEncoderRepository;
import com.example.outings.auth.application.port.out.UserRepository;
import com.example.outings.auth.domain.exceptions.InvalidCredentialsException;
import com.example.outings.auth.domain.exceptions.UserNotFoundException;
import com.example.outings.auth.domain.model.Role;
import com.example.outings.auth.domain.model.User;

@Service
public class LoginCommandHandler implements LoginUseCase{

    private final UserRepository userRepository;

    private final PasswordEncoderRepository passwordEncoder;

    public LoginCommandHandler(UserRepository userRepository, PasswordEncoderRepository passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

   
    public LoginResult handle(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
            .orElseThrow(() -> new UserNotFoundException(command.email()));

        if(!passwordEncoder.matches(command.password(), user.getPassword())){
            throw new InvalidCredentialsException();
        }

        Set<Role> authorities = user.getRoles();

        return new LoginResult(command.email(), authorities);
    }
    
}
