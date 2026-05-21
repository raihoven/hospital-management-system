package com.amiradilzhanaisha.hospitalmanagementsystem.dto;

public record AmirAdilzhanAishaAuthResponse(
        String token,
        String tokenType,
        String email,
        String role
) {
}
