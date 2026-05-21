package com.turatbekuly.amir.hospitalmanagementsystem.dto;

public record TuratbekulyAmirPatientAnalyticsDto(
        long totalPatients,
        double averageAge,
        long adultPatients,
        long minorPatients,
        long patientsWithIllness
) {
}
