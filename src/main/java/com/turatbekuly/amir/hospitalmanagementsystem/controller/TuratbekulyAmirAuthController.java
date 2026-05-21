package com.turatbekuly.amir.hospitalmanagementsystem.controller;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirAuthResponse;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirLoginRequest;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirRegisterRequest;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class TuratbekulyAmirAuthController {

    private final TuratbekulyAmirAuthService authService;

    public TuratbekulyAmirAuthController(TuratbekulyAmirAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TuratbekulyAmirAuthResponse> register(@Valid @RequestBody TuratbekulyAmirRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TuratbekulyAmirAuthResponse> login(@Valid @RequestBody TuratbekulyAmirLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
