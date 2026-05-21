package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TuratbekulyAmirPatientDto(
        Long id,
        @NotBlank(message = "First name must not be blank")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,
        @NotBlank(message = "Last name must not be blank")
        String lastName,
        @Positive(message = "Age must be greater than 0")
        int age,
        String illness
) {
}
