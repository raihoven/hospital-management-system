package com.amiradilzhanaisha.hospitalmanagementsystem.dto;

public record AmirAdilzhanAishaFileAuditDto(
        long totalFiles,
        long availableFiles,
        long missingFiles,
        long totalSizeBytes,
        String storagePath
) {
}
