package com.ubistart.challenge.main.repositories;

import com.ubistart.challenge.main.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
