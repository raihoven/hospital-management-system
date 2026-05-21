package com.turatbekuly.amir.hospitalmanagementsystem.dto;

import com.turatbekuly.amir.hospitalmanagementsystem.entity.AmirAdilzhanAishaAppointmentStatus;

import java.time.LocalDateTime;

public record AmirAdilzhanAishaAppointmentDto(
        Long id,
        LocalDateTime appointmentDateTime,
        AmirAdilzhanAishaAppointmentStatus status,
        String notes,
        Long patientId,
        Long doctorId
) {
}
