package com.example.outings.auth.infrastructure.out.persistence;

import org.springframework.stereotype.Component;

import com.example.outings.auth.application.port.out.RoleRepository;
import com.example.outings.auth.domain.model.Role;

@Component
public class RoleAdapter implements RoleRepository {
    private final RoleJpaRepository roleJpaRepository;
    private final Mapper mapper;

    public RoleAdapter(RoleJpaRepository roleJpaRepository, Mapper mapper){
        this.roleJpaRepository = roleJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Role findByName(String name) {      
        return mapper.entityToModel(roleJpaRepository.findByName(name));
    }
    
}
