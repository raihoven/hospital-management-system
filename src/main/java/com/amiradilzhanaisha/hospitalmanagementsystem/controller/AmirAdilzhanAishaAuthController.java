package com.amiradilzhanaisha.hospitalmanagementsystem.controller;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaAuthResponse;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaLoginRequest;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaRegisterRequest;
import com.amiradilzhanaisha.hospitalmanagementsystem.service.AmirAdilzhanAishaAuthService;
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
public class AmirAdilzhanAishaAuthController {

    private final AmirAdilzhanAishaAuthService authService;

    public AmirAdilzhanAishaAuthController(AmirAdilzhanAishaAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user with ROLE_USER and returns a JWT token")
    public ResponseEntity<AmirAdilzhanAishaAuthResponse> register(@Valid @RequestBody AmirAdilzhanAishaRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates by email and password and returns a JWT token")
    public ResponseEntity<AmirAdilzhanAishaAuthResponse> login(@Valid @RequestBody AmirAdilzhanAishaLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
