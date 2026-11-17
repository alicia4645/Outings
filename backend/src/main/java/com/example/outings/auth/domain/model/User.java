package com.example.outings.auth.domain.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;

    private String name;
    private String username;

    private String email;
    private String password;

    private Set<Role> roles;
}
