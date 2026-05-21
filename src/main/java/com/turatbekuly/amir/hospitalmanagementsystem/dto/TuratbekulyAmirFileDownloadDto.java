package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import org.springframework.core.io.Resource;

public record TuratbekulyAmirFileDownloadDto(
        String originalFileName,
        String contentType,
        Resource resource
) {
}
