package com.amiradilzhanaisha.hospitalmanagementsystem.dto;

import com.amiradilzhanaisha.hospitalmanagementsystem.entity.AmirAdilzhanAishaAppointmentStatus;

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
