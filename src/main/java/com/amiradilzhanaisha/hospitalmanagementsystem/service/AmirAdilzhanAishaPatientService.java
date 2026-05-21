package com.amiradilzhanaisha.hospitalmanagementsystem.service;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPagedResponseDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientDto;
import jakarta.validation.Valid;

public interface AmirAdilzhanAishaPatientService {

    AmirAdilzhanAishaPagedResponseDto<AmirAdilzhanAishaPatientDto> getAllPatients(
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

    AmirAdilzhanAishaPatientDto createPatient(@Valid AmirAdilzhanAishaPatientDto patientDto);

    AmirAdilzhanAishaPatientDto getPatientById(Long id);

    AmirAdilzhanAishaPatientDto updatePatient(Long id, @Valid AmirAdilzhanAishaPatientDto patientDto);

    void deletePatient(Long id);
}
