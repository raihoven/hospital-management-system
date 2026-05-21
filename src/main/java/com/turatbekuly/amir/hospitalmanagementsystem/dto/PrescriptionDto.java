package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDate;

public record PrescriptionDto(
        Long id,
        String medicationName,
        String dosage,
        String instructions,
        LocalDate issuedDate,
        Long patientId,
        Long doctorId
) {
}
