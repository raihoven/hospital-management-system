package com.turatbekuly.amir.hospitalmanagementsystem.config;

import com.turatbekuly.amir.hospitalmanagementsystem.entity.AmirAdilzhanAishaRole;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.AmirAdilzhanAishaUser;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.AmirAdilzhanAishaUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AmirAdilzhanAishaSecurityDataInitializer {

    @Bean
    public CommandLineRunner createDefaultAdmin(
            AmirAdilzhanAishaUserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            String adminEmail = "admin@hospital.local";
            if (!userRepository.existsByEmail(adminEmail)) {
                AmirAdilzhanAishaUser admin = new AmirAdilzhanAishaUser(
                        "Amir",
                        "AdilzhanAisha",
                        adminEmail,
                        passwordEncoder.encode("Admin123!"),
                        AmirAdilzhanAishaRole.ROLE_ADMIN
                );
                userRepository.save(admin);
            }
        };
    }
}
