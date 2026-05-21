package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPagedResponseDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPatientDto;
import jakarta.validation.Valid;

public interface TuratbekulyAmirPatientService {

    TuratbekulyAmirPagedResponseDto<TuratbekulyAmirPatientDto> getAllPatients(
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

    TuratbekulyAmirPatientDto createPatient(@Valid TuratbekulyAmirPatientDto patientDto);

    TuratbekulyAmirPatientDto getPatientById(Long id);

    TuratbekulyAmirPatientDto updatePatient(Long id, @Valid TuratbekulyAmirPatientDto patientDto);

    void deletePatient(Long id);
}
