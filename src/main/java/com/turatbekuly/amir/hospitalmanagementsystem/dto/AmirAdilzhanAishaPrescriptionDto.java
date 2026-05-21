package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDate;

public record AmirAdilzhanAishaPrescriptionDto(
        Long id,
        String medicationName,
        String dosage,
        String instructions,
        LocalDate issuedDate,
        Long patientId,
        Long doctorId
) {
}
