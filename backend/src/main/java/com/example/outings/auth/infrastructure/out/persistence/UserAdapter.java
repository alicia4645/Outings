package com.example.outings.auth.infrastructure.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.outings.auth.application.port.out.UserRepository;
import com.example.outings.auth.domain.model.User;

@Component 
public class UserAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    
    private final Mapper mapper;

    public UserAdapter(UserJpaRepository userJpaRepository, Mapper mapper){
        this.userJpaRepository = userJpaRepository;
        this.mapper = mapper;
    }

    @Override 
    public Optional<User> findByEmail(String email){
        return userJpaRepository.findByEmail(email).map(mapper::entityToModel);
    }
   
    @Override
    public Boolean existsByEmail(String email){
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity =  userJpaRepository.save(mapper.modelToEntity(user));
        return mapper.entityToModel(userEntity);
    }
}
