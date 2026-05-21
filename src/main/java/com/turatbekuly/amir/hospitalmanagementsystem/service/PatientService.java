package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.PagedResponseDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.PatientDto;
import jakarta.validation.Valid;

public interface PatientService {

    PagedResponseDto<PatientDto> getAllPatients(
            String search,
            String firstName,
            String lastName,
            String illness,
            Integer minAge,
            Integer maxAge,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    PatientDto createPatient(@Valid PatientDto patientDto);

    PatientDto getPatientById(Long id);

    PatientDto updatePatient(Long id, @Valid PatientDto patientDto);

    boolean deletePatient(Long id);
}
