package com.example.outings.auth.application.port.out;

import com.example.outings.auth.domain.model.Role;

public interface RoleRepository {
    Role findByName(String name);
}
