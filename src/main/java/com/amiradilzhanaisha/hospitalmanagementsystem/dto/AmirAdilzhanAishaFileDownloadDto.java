package com.amiradilzhanaisha.hospitalmanagementsystem.dto;

import org.springframework.core.io.Resource;

public record AmirAdilzhanAishaFileDownloadDto(
        String originalFileName,
        String contentType,
        Resource resource
) {
}
