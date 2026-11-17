package com.example.outings.auth.infrastructure.out.persistence;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.outings.auth.domain.model.User;
import com.example.outings.auth.domain.model.Role;

@Component
public class Mapper {

    public Mapper() {}

    public Set<Role> entityToModel(Set<RoleEntity> roleEntity){

        Set<Role> role = roleEntity
            .stream()
            .map(re -> new Role(re.getId(), re.getName()))
            .collect(Collectors.toSet());

        return role;
    }

    
    public User entityToModel(UserEntity userEntity){
        Set<Role> role = entityToModel(userEntity.getRoles());

        return new User(
            userEntity.getId(),
            userEntity.getName(),
            userEntity.getUsername(),
            userEntity.getEmail(),
            userEntity.getPassword(),
            role
        );
    }


    public Set<RoleEntity> modelToEntity(Set<Role> role){

        Set<RoleEntity> roleEntity = role
            .stream()
            .map(r -> new RoleEntity(r.getId(), r.getName()))
            .collect(Collectors.toSet());

        return roleEntity;
    }

    public UserEntity modelToEntity(User user){
        Set<RoleEntity> roleEntity = modelToEntity(user.getRoles());

        return new UserEntity(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            roleEntity
        );
    }

    public Role entityToModel(RoleEntity roleEntity){
        return new Role(roleEntity.getId(), roleEntity.getName());
    }

    public RoleEntity modelToEntity(Role role){
        return new RoleEntity(role.getId(), role.getName());
    }


}

            