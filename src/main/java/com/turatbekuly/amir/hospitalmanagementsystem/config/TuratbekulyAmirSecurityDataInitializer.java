package com.turatbekuly.amir.hospitalmanagementsystem.config;

import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirRole;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirUser;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.TuratbekulyAmirUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class TuratbekulyAmirSecurityDataInitializer {

    @Bean
    public CommandLineRunner createDefaultAdmin(
            TuratbekulyAmirUserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            String adminEmail = "admin@hospital.local";
            if (!userRepository.existsByEmail(adminEmail)) {
                TuratbekulyAmirUser admin = new TuratbekulyAmirUser(
                        "Amir",
                        "Turatbekuly",
                        adminEmail,
                        passwordEncoder.encode("Admin123!"),
                        TuratbekulyAmirRole.ROLE_ADMIN
                );
                userRepository.save(admin);
            }
        };
    }
}
