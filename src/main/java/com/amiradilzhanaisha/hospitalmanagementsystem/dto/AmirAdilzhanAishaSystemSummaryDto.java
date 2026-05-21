package com.amiradilzhanaisha.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;

public record AmirAdilzhanAishaSystemSummaryDto(
        long totalUsers,
        long totalAdmins,
        long totalPatients,
        long totalFiles,
        LocalDateTime generatedAt
) {
}
