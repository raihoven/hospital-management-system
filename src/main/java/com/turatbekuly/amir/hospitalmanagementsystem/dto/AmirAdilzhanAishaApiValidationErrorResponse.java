package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record AmirAdilzhanAishaApiValidationErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
}
