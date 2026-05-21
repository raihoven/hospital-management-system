package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;

public record TuratbekulyAmirSystemSummaryDto(
        long totalUsers,
        long totalAdmins,
        long totalPatients,
        long totalFiles,
        LocalDateTime generatedAt
) {
}
