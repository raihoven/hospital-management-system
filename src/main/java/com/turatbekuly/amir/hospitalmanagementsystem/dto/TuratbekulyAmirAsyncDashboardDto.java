package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;

public record TuratbekulyAmirAsyncDashboardDto(
        TuratbekulyAmirPatientAnalyticsDto patientAnalytics,
        TuratbekulyAmirFileAuditDto fileAudit,
        TuratbekulyAmirSystemSummaryDto systemSummary,
        LocalDateTime generatedAt
) {
}
