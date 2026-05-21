package com.turatbekuly.amir.hospitalmanagementsystem.service.impl;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.PatientDto;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.Patient;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.PatientNotFoundException;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.PatientRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientDto> getAllPatients(String firstName, String lastName, String illness) {
        return patientRepository.findAll(buildPatientSpecification(firstName, lastName, illness))
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public PatientDto createPatient(@Valid PatientDto patientDto) {
        Patient savedPatient = patientRepository.save(toEntity(patientDto));
        return toDto(savedPatient);
    }

    @Override
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        return toDto(patient);
    }

    @Override
    public PatientDto updatePatient(Long id, @Valid PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        patient.setFirstName(patientDto.firstName());
        patient.setLastName(patientDto.lastName());
        patient.setAge(patientDto.age());
        patient.setIllness(patientDto.illness());

        Patient updatedPatient = patientRepository.save(patient);
        return toDto(updatedPatient);
    }

    @Override
    public boolean deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            return false;
        }

        patientRepository.deleteById(id);
        return true;
    }

    private Specification<Patient> buildPatientSpecification(String firstName, String lastName, String illness) {
        Specification<Patient> specification = Specification.where(null);

        if (StringUtils.hasText(firstName)) {
            String normalizedFirstName = "%" + firstName.trim().toLowerCase() + "%";
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), normalizedFirstName)
            );
        }

        if (StringUtils.hasText(lastName)) {
            String normalizedLastName = "%" + lastName.trim().toLowerCase() + "%";
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), normalizedLastName)
            );
        }

        if (StringUtils.hasText(illness)) {
            String normalizedIllness = "%" + illness.trim().toLowerCase() + "%";
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("illness")), normalizedIllness)
            );
        }

        return specification;
    }

    private PatientDto toDto(Patient patient) {
        return new PatientDto(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getAge(),
                patient.getIllness()
        );
    }

    private Patient toEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientDto.firstName());
        patient.setLastName(patientDto.lastName());
        patient.setAge(patientDto.age());
        patient.setIllness(patientDto.illness());
        return patient;
    }
}
