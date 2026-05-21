package com.turatbekuly.amir.hospitalmanagementsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmirAdilzhanAishaOpenApiConfig {

    private static final String BEARER_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI AmirAdilzhanAishaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Amir Adilzhan Aisha Hospital Management System API")
                        .version("v1")
                        .description("REST API for hospital management system with patients, files, async analytics and JWT security")
                        .contact(new Contact()
                                .name("Amir Adilzhan Aisha")
                                .email("amir.adilzhan.aisha@example.com")
                        )
                )
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME_NAME, new SecurityScheme()
                                .name(BEARER_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}
