package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirAppointmentStatus;

import java.time.LocalDateTime;

public record TuratbekulyAmirAppointmentDto(
        Long id,
        LocalDateTime appointmentDateTime,
        TuratbekulyAmirAppointmentStatus status,
        String notes,
        Long patientId,
        Long doctorId
) {
}
