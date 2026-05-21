package com.amiradilzhanaisha.hospitalmanagementsystem.config;

import com.amiradilzhanaisha.hospitalmanagementsystem.security.AmirAdilzhanAishaAccessDeniedHandler;
import com.amiradilzhanaisha.hospitalmanagementsystem.security.AmirAdilzhanAishaAuthenticationEntryPoint;
import com.amiradilzhanaisha.hospitalmanagementsystem.security.AmirAdilzhanAishaJwtAuthenticationFilter;
import com.amiradilzhanaisha.hospitalmanagementsystem.security.AmirAdilzhanAishaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class AmirAdilzhanAishaSecurityConfig {

    private final AmirAdilzhanAishaJwtAuthenticationFilter jwtAuthenticationFilter;
    private final AmirAdilzhanAishaUserDetailsService userDetailsService;
    private final AmirAdilzhanAishaAuthenticationEntryPoint authenticationEntryPoint;
    private final AmirAdilzhanAishaAccessDeniedHandler accessDeniedHandler;

    public AmirAdilzhanAishaSecurityConfig(
            AmirAdilzhanAishaJwtAuthenticationFilter jwtAuthenticationFilter,
            AmirAdilzhanAishaUserDetailsService userDetailsService,
            AmirAdilzhanAishaAuthenticationEntryPoint authenticationEntryPoint,
            AmirAdilzhanAishaAccessDeniedHandler accessDeniedHandler
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/error", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/actuator/health/**", "/actuator/info").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/async/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/files/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/files/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/patients/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/patients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/patients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
