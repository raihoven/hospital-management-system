package com.amiradilzhanaisha.hospitalmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AmirAdilzhanAishaLoginRequest(
        @Email(message = "Email должен быть корректным")
        @NotBlank(message = "Email не должен быть пустым")
        String email,
        @NotBlank(message = "Пароль не должен быть пустым")
        String password
) {
}
