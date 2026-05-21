package com.turatbekuly.amir.hospitalmanagementsystem.service.impl;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.PagedResponseDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.PatientDto;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.Patient;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.PatientNotFoundException;
import com.turatbekuly.amir.hospitalmanagementsystem.mapper.TuratbekulyAmirEntityMapper;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.PatientRepository;
import com.turatbekuly.amir.hospitalmanagementsystem.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Service
@Validated
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "firstName", "lastName", "age", "illness");

    private final PatientRepository patientRepository;
    private final TuratbekulyAmirEntityMapper entityMapper;

    public PatientServiceImpl(PatientRepository patientRepository, TuratbekulyAmirEntityMapper entityMapper) {
        this.patientRepository = patientRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public PagedResponseDto<PatientDto> getAllPatients(
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
    ) {
        log.info(
                "Fetching patients with search='{}', firstName='{}', lastName='{}', illness='{}', minAge={}, maxAge={}, page={}, size={}, sortBy='{}', sortDir='{}'",
                search, firstName, lastName, illness, minAge, maxAge, page, size, sortBy, sortDir
        );

        Pageable pageable = PageRequest.of(
                normalizePage(page),
                normalizeSize(size),
                buildSort(sortBy, sortDir)
        );

        Page<PatientDto> patientPage = patientRepository
                .findAll(buildPatientSpecification(search, firstName, lastName, illness, minAge, maxAge), pageable)
                .map(entityMapper::toPatientDto);

        log.info("Fetched patient page {} with {} elements out of total {}", patientPage.getNumber(), patientPage.getNumberOfElements(), patientPage.getTotalElements());

        return new PagedResponseDto<>(
                patientPage.getContent(),
                patientPage.getNumber(),
                patientPage.getSize(),
                patientPage.getTotalElements(),
                patientPage.getTotalPages(),
                patientPage.isLast()
        );
    }

    @Override
    public PatientDto createPatient(@Valid PatientDto patientDto) {
        Patient savedPatient = patientRepository.save(entityMapper.toPatientEntity(patientDto));
        log.info("Created patient id={} firstName='{}' lastName='{}'", savedPatient.getId(), savedPatient.getFirstName(), savedPatient.getLastName());
        return entityMapper.toPatientDto(savedPatient);
    }

    @Override
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        log.info("Fetched patient by id={}", id);
        return entityMapper.toPatientDto(patient);
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
        log.info("Updated patient id={} firstName='{}' lastName='{}'", updatedPatient.getId(), updatedPatient.getFirstName(), updatedPatient.getLastName());
        return entityMapper.toPatientDto(updatedPatient);
    }

    @Override
    public boolean deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            log.warn("Attempted to delete non-existing patient id={}", id);
            return false;
        }

        patientRepository.deleteById(id);
        log.info("Deleted patient id={}", id);
        return true;
    }

    private Specification<Patient> buildPatientSpecification(
            String search,
            String firstName,
            String lastName,
            String illness,
            Integer minAge,
            Integer maxAge
    ) {
        Specification<Patient> specification = Specification.where(null);

        if (StringUtils.hasText(search)) {
            String normalizedSearch = "%" + search.trim().toLowerCase() + "%";
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), normalizedSearch),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), normalizedSearch),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("illness")), normalizedSearch)
                    )
            );
        }

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

        if (minAge != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("age"), minAge)
            );
        }

        if (maxAge != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("age"), maxAge)
            );
        }

        return specification;
    }

    private Sort buildSort(String sortBy, String sortDir) {
        String normalizedSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "id";
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, normalizedSortBy);
    }

    private int normalizePage(int page) {
        return Math.max(page, 0);
    }

    private int normalizeSize(int size) {
        if (size <= 0) {
            return 10;
        }
        return Math.min(size, 100);
    }

}
