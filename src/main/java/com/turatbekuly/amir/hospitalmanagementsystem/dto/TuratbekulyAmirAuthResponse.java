package com.turatbekuly.amir.hospitalmanagementsystem.dto;

public record TuratbekulyAmirAuthResponse(
        String token,
        String tokenType,
        String email,
        String role
) {
}
