package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;

public record AmirAdilzhanAishaFileDto(
        Long id,
        String originalFileName,
        String contentType,
        Long size,
        LocalDateTime uploadedAt,
        String downloadUrl
) {
}
