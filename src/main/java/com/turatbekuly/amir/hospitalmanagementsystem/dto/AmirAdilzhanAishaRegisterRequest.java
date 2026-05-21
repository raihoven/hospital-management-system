package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AmirAdilzhanAishaRegisterRequest(
        @NotBlank(message = "Имя не должно быть пустым")
        @Size(max = 50, message = "Имя не должно превышать 50 символов")
        String firstName,
        @NotBlank(message = "Фамилия не должна быть пустой")
        @Size(max = 50, message = "Фамилия не должна превышать 50 символов")
        String lastName,
        @Email(message = "Email должен быть корректным")
        @NotBlank(message = "Email не должен быть пустым")
        String email,
        @NotBlank(message = "Пароль не должен быть пустым")
        @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
        String password
) {
}
