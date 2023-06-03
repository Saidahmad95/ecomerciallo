package com.example.ecommerciallo.repos;

import com.example.ecommerciallo.entities.Role;
import com.example.ecommerciallo.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Long> {


    Optional<Role> findByRoleName(ERole roleName);
}
