package com.library.management.config;

import com.library.management.model.Role;
import com.library.management.model.RoleName;
import com.library.management.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        if (roleRepository.count() == 0) {
            Role adminRole = new Role(RoleName.ROLE_ADMIN);
            Role librarianRole = new Role(RoleName.ROLE_LIBRARIAN);
            Role userRole = new Role(RoleName.ROLE_USER);

            roleRepository.save(adminRole);
            roleRepository.save(librarianRole);
            roleRepository.save(userRole);
        }
    }
}