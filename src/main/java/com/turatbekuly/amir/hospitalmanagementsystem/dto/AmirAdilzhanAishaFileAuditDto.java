package com.turatbekuly.amir.hospitalmanagementsystem.dto;

public record AmirAdilzhanAishaFileAuditDto(
        long totalFiles,
        long availableFiles,
        long missingFiles,
        long totalSizeBytes,
        String storagePath
) {
}
