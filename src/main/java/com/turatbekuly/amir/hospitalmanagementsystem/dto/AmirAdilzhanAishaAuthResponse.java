package com.turatbekuly.amir.hospitalmanagementsystem.dto;

public record AmirAdilzhanAishaAuthResponse(
        String token,
        String tokenType,
        String email,
        String role
) {
}
