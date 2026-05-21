package com.turatbekuly.amir.hospitalmanagementsystem.dto;

public record TuratbekulyAmirDoctorDto(
        Long id,
        String firstName,
        String lastName,
        String specialization,
        String email,
        Long departmentId,
        String departmentName
) {
}
