package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;

public record TuratbekulyAmirMedicalRecordDto(
        Long id,
        String diagnosis,
        String treatmentPlan,
        LocalDateTime createdAt,
        Long patientId,
        Long doctorId
) {
}
