package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import com.turatbekuly.amir.hospitalmanagementsystem.entity.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentDto(
        Long id,
        LocalDateTime appointmentDateTime,
        AppointmentStatus status,
        String notes,
        Long patientId,
        Long doctorId
) {
}
