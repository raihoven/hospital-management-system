package com.amiradilzhanaisha.hospitalmanagementsystem.service.impl;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaAuthResponse;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaLoginRequest;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaRegisterRequest;
import com.amiradilzhanaisha.hospitalmanagementsystem.entity.AmirAdilzhanAishaRole;
import com.amiradilzhanaisha.hospitalmanagementsystem.entity.AmirAdilzhanAishaUser;
import com.amiradilzhanaisha.hospitalmanagementsystem.exception.AmirAdilzhanAishaUserAlreadyExistsException;
import com.amiradilzhanaisha.hospitalmanagementsystem.repository.AmirAdilzhanAishaUserRepository;
import com.amiradilzhanaisha.hospitalmanagementsystem.security.AmirAdilzhanAishaJwtService;
import com.amiradilzhanaisha.hospitalmanagementsystem.service.AmirAdilzhanAishaAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AmirAdilzhanAishaAuthServiceImpl implements AmirAdilzhanAishaAuthService {

    private static final Logger log = LoggerFactory.getLogger(AmirAdilzhanAishaAuthServiceImpl.class);
    private final AmirAdilzhanAishaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmirAdilzhanAishaJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AmirAdilzhanAishaAuthServiceImpl(
            AmirAdilzhanAishaUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AmirAdilzhanAishaJwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AmirAdilzhanAishaAuthResponse register(AmirAdilzhanAishaRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            log.warn("Registration rejected for existing email={}", request.email());
            throw new AmirAdilzhanAishaUserAlreadyExistsException(request.email());
        }

        AmirAdilzhanAishaUser user = new AmirAdilzhanAishaUser(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password()),
                AmirAdilzhanAishaRole.ROLE_USER
        );

        AmirAdilzhanAishaUser savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        log.info("Registered new user email={} role={}", savedUser.getEmail(), savedUser.getRole());
        return new AmirAdilzhanAishaAuthResponse(token, "Bearer", savedUser.getEmail(), savedUser.getRole().name());
    }

    @Override
    public AmirAdilzhanAishaAuthResponse login(AmirAdilzhanAishaLoginRequest request) {
        log.info("Authentication attempt for email={}", request.email());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        AmirAdilzhanAishaUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден после аутентификации"));

        String token = jwtService.generateToken(user);
        log.info("Authentication successful for email={} role={}", user.getEmail(), user.getRole());
        return new AmirAdilzhanAishaAuthResponse(token, "Bearer", user.getEmail(), user.getRole().name());
    }
}
