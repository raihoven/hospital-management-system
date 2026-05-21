package com.turatbekuly.amir.hospitalmanagementsystem.controller;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirAuthResponse;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirLoginRequest;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirRegisterRequest;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Authentication", description = "Registration and login endpoints")
public class TuratbekulyAmirAuthController {

    private final TuratbekulyAmirAuthService authService;

    public TuratbekulyAmirAuthController(TuratbekulyAmirAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user with ROLE_USER and returns a JWT token")
    public ResponseEntity<TuratbekulyAmirAuthResponse> register(@Valid @RequestBody TuratbekulyAmirRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates by email and password and returns a JWT token")
    public ResponseEntity<TuratbekulyAmirAuthResponse> login(@Valid @RequestBody TuratbekulyAmirLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
