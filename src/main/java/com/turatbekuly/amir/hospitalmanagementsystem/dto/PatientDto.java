package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PatientDto(
        Long id,
        @NotBlank(message = "Имя не должно быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String firstName,
        @NotBlank(message = "Фамилия не должна быть пустой")
        String lastName,
        @Positive(message = "Возраст должен быть больше 0")
        int age,
        String illness
) {
}
