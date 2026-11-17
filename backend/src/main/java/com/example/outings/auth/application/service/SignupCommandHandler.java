package com.example.outings.auth.application.service;

import com.example.outings.auth.application.port.in.SignupUseCase;
import com.example.outings.auth.application.port.out.UserRepository;
import com.example.outings.auth.domain.exceptions.UserAlreadyExistsException;
import com.example.outings.auth.domain.model.User;
import com.example.outings.auth.domain.model.Role;
import com.example.outings.auth.application.port.out.RoleRepository;
import com.example.outings.auth.application.port.out.PasswordEncoderRepository;
import com.example.outings.auth.application.port.command.SignupCommand; 

import java.util.Set;

import org.springframework.stereotype.Service;


@Service 
public class SignupCommandHandler implements SignupUseCase {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    
    private final PasswordEncoderRepository passwordEncoder;

    public SignupCommandHandler(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoderRepository passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public void handle(SignupCommand command){

        if(userRepository.existsByEmail(command.email())){
            throw new UserAlreadyExistsException(command.email());
        }

        User user = new User();
        user.setEmail(command.email());
        user.setName(command.name());
        user.setUsername(command.username());
        user.setPassword(passwordEncoder.encode(command.password()));

        Role roleEntity = roleRepository.findByName("ROLE_USER");
        user.setRoles(Set.of(roleEntity));

        userRepository.save(user);
    }
}
