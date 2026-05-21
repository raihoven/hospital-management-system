package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import org.springframework.core.io.Resource;

public record AmirAdilzhanAishaFileDownloadDto(
        String originalFileName,
        String contentType,
        Resource resource
) {
}
