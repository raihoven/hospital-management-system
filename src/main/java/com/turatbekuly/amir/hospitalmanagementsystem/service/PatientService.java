package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.PatientDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PatientService {

    List<PatientDto> getAllPatients();

    PatientDto createPatient(@Valid PatientDto patientDto);

    PatientDto getPatientById(Long id);

    PatientDto updatePatient(Long id, @Valid PatientDto patientDto);

    boolean deletePatient(Long id);
}
