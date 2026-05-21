package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;

public record MedicalRecordDto(
        Long id,
        String diagnosis,
        String treatmentPlan,
        LocalDateTime createdAt,
        Long patientId,
        Long doctorId
) {
}
