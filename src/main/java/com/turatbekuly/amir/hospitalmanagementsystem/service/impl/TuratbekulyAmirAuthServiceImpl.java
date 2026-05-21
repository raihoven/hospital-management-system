package com.turatbekuly.amir.hospitalmanagementsystem.service.impl;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirAuthResponse;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirLoginRequest;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirRegisterRequest;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirRole;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirUser;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.TuratbekulyAmirUserAlreadyExistsException;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.TuratbekulyAmirUserRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.security.TuratbekulyAmirJwtService;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TuratbekulyAmirAuthServiceImpl implements TuratbekulyAmirAuthService {

    private final TuratbekulyAmirUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TuratbekulyAmirJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TuratbekulyAmirAuthServiceImpl(
            TuratbekulyAmirUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TuratbekulyAmirJwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TuratbekulyAmirAuthResponse register(TuratbekulyAmirRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new TuratbekulyAmirUserAlreadyExistsException(request.email());
        }

        TuratbekulyAmirUser user = new TuratbekulyAmirUser(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password()),
                TuratbekulyAmirRole.ROLE_USER
        );

        TuratbekulyAmirUser savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return new TuratbekulyAmirAuthResponse(token, "Bearer", savedUser.getEmail(), savedUser.getRole().name());
    }

    @Override
    public TuratbekulyAmirAuthResponse login(TuratbekulyAmirLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        TuratbekulyAmirUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден после аутентификации"));

        String token = jwtService.generateToken(user);
        return new TuratbekulyAmirAuthResponse(token, "Bearer", user.getEmail(), user.getRole().name());
    }
}
