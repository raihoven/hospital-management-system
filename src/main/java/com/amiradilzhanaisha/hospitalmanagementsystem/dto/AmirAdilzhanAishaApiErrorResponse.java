package com.amiradilzhanaisha.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;

public record AmirAdilzhanAishaApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
