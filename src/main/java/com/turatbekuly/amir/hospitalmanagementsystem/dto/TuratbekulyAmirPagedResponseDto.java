package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import java.util.List;

public record TuratbekulyAmirPagedResponseDto<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {
}
