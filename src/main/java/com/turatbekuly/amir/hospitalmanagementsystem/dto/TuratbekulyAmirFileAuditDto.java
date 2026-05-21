package com.turatbekuly.amir.hospitalmanagementsystem.dto;

public record TuratbekulyAmirFileAuditDto(
        long totalFiles,
        long availableFiles,
        long missingFiles,
        long totalSizeBytes,
        String storagePath
) {
}
