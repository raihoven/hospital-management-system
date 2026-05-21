package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;

public record AmirAdilzhanAishaAsyncDashboardDto(
        AmirAdilzhanAishaPatientAnalyticsDto patientAnalytics,
        AmirAdilzhanAishaFileAuditDto fileAudit,
        AmirAdilzhanAishaSystemSummaryDto systemSummary,
        LocalDateTime generatedAt
) {
}
