package com.example.ecommerciallo.util;

import com.example.ecommerciallo.entities.Role;
import com.example.ecommerciallo.enums.ERole;
import com.example.ecommerciallo.repos.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomRunner implements ApplicationRunner {

    private final RoleRepo roleRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleRepo.findAll().isEmpty()) {
            for (ERole eRole : ERole.values()) {
                Role builtRole = Role.builder()
                        .roleName(eRole)
                        .build();
                roleRepo.save(builtRole);
            }
        }
    }
}
