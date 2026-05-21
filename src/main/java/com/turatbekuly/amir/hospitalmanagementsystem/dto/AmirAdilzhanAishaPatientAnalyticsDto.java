package com.turatbekuly.amir.hospitalmanagementsystem.dto;

public record AmirAdilzhanAishaPatientAnalyticsDto(
        long totalPatients,
        double averageAge,
        long adultPatients,
        long minorPatients,
        long patientsWithIllness
) {
}
