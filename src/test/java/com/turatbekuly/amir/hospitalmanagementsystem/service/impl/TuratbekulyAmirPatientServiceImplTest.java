package com.turatbekuly.amir.hospitalmanagementsystem.service.impl;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPagedResponseDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPatientDto;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.TuratbekulyAmirPatient;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.TuratbekulyAmirPatientNotFoundException;
import com.turatbekuly.amir.hospitalmanagementsystem.mapper.TuratbekulyAmirEntityMapper;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.TuratbekulyAmirPatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TuratbekulyAmirPatientServiceImplTest {

    @Mock
    private TuratbekulyAmirPatientRepository patientRepository;

    @Mock
    private TuratbekulyAmirEntityMapper entityMapper;

    @InjectMocks
    private TuratbekulyAmirPatientServiceImpl patientService;

    @Test
    void getAllPatientsNormalizesPaginationSortAndReturnsMappedPage() {
        TuratbekulyAmirPatient TuratbekulyAmirPatient = TuratbekulyAmirPatient(1L, "Amir", "Turatbekuly", 24, "flu");
        TuratbekulyAmirPatientDto dto = new TuratbekulyAmirPatientDto(1L, "Amir", "Turatbekuly", 24, "flu");

        when(patientRepository.findAll(anySpecification(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(TuratbekulyAmirPatient)));
        when(entityMapper.toPatientDto(TuratbekulyAmirPatient)).thenReturn(dto);

        TuratbekulyAmirPagedResponseDto<TuratbekulyAmirPatientDto> response = patientService.getAllPatients(
                "tur", "am", "tur", "flu", 18, 70, -4, 500, "age", "desc"
        );

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(patientRepository).findAll(anySpecification(), pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(100);
        assertThat(pageable.getSort().getOrderFor("age")).isNotNull();
        assertThat(pageable.getSort().getOrderFor("age").getDirection()).isEqualTo(Sort.Direction.DESC);
        assertThat(response.content()).containsExactly(dto);
        assertThat(response.totalElements()).isEqualTo(1);
    }

    @Test
    void getPatientByIdThrowsDomainExceptionWhenMissing() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.getPatientById(99L))
                .isInstanceOf(TuratbekulyAmirPatientNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updatePatientChangesExistingEntity() {
        TuratbekulyAmirPatient existing = TuratbekulyAmirPatient(7L, "Old", "Name", 30, "cold");
        TuratbekulyAmirPatientDto request = new TuratbekulyAmirPatientDto(null, "New", "TuratbekulyAmirPatient", 31, "healthy");
        TuratbekulyAmirPatientDto response = new TuratbekulyAmirPatientDto(7L, "New", "TuratbekulyAmirPatient", 31, "healthy");

        when(patientRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(patientRepository.save(existing)).thenReturn(existing);
        when(entityMapper.toPatientDto(existing)).thenReturn(response);

        TuratbekulyAmirPatientDto updated = patientService.updatePatient(7L, request);

        assertThat(existing.getFirstName()).isEqualTo("New");
        assertThat(existing.getLastName()).isEqualTo("TuratbekulyAmirPatient");
        assertThat(existing.getAge()).isEqualTo(31);
        assertThat(existing.getIllness()).isEqualTo("healthy");
        assertThat(updated).isEqualTo(response);
    }

    @Test
    void deletePatientUsesExceptionFlowWhenMissing() {
        when(patientRepository.existsById(42L)).thenReturn(false);

        assertThatThrownBy(() -> patientService.deletePatient(42L))
                .isInstanceOf(TuratbekulyAmirPatientNotFoundException.class);
        verify(patientRepository, never()).deleteById(42L);
    }

    @Test
    void deletePatientRemovesExistingPatient() {
        when(patientRepository.existsById(5L)).thenReturn(true);

        patientService.deletePatient(5L);

        verify(patientRepository).deleteById(5L);
    }

    @SuppressWarnings("unchecked")
    private Specification<TuratbekulyAmirPatient> anySpecification() {
        return any(Specification.class);
    }

    private TuratbekulyAmirPatient TuratbekulyAmirPatient(Long id, String firstName, String lastName, int age, String illness) {
        TuratbekulyAmirPatient TuratbekulyAmirPatient = new TuratbekulyAmirPatient(firstName, lastName, age, illness);
        TuratbekulyAmirPatient.setId(id);
        return TuratbekulyAmirPatient;
    }
}
