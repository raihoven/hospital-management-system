package com.turatbekuly.amir.hospitalmanagementsystem.service.impl;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaPagedResponseDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientDto;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.AmirAdilzhanAishaPatient;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.AmirAdilzhanAishaPatientNotFoundException;
import com.turatbekuly.amir.hospitalmanagementsystem.mapper.AmirAdilzhanAishaEntityMapper;
import com.turatbekuly.amir.hospitalmanagementsystem.repository.AmirAdilzhanAishaPatientRepository;
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
class AmirAdilzhanAishaPatientServiceImplTest {

    @Mock
    private AmirAdilzhanAishaPatientRepository patientRepository;

    @Mock
    private AmirAdilzhanAishaEntityMapper entityMapper;

    @InjectMocks
    private AmirAdilzhanAishaPatientServiceImpl patientService;

    @Test
    void getAllPatientsNormalizesPaginationSortAndReturnsMappedPage() {
        AmirAdilzhanAishaPatient AmirAdilzhanAishaPatient = AmirAdilzhanAishaPatient(1L, "Amir", "AdilzhanAisha", 24, "flu");
        AmirAdilzhanAishaPatientDto dto = new AmirAdilzhanAishaPatientDto(1L, "Amir", "AdilzhanAisha", 24, "flu");

        when(patientRepository.findAll(anySpecification(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(AmirAdilzhanAishaPatient)));
        when(entityMapper.toPatientDto(AmirAdilzhanAishaPatient)).thenReturn(dto);

        AmirAdilzhanAishaPagedResponseDto<AmirAdilzhanAishaPatientDto> response = patientService.getAllPatients(
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
                .isInstanceOf(AmirAdilzhanAishaPatientNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updatePatientChangesExistingEntity() {
        AmirAdilzhanAishaPatient existing = AmirAdilzhanAishaPatient(7L, "Old", "Name", 30, "cold");
        AmirAdilzhanAishaPatientDto request = new AmirAdilzhanAishaPatientDto(null, "New", "AmirAdilzhanAishaPatient", 31, "healthy");
        AmirAdilzhanAishaPatientDto response = new AmirAdilzhanAishaPatientDto(7L, "New", "AmirAdilzhanAishaPatient", 31, "healthy");

        when(patientRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(patientRepository.save(existing)).thenReturn(existing);
        when(entityMapper.toPatientDto(existing)).thenReturn(response);

        AmirAdilzhanAishaPatientDto updated = patientService.updatePatient(7L, request);

        assertThat(existing.getFirstName()).isEqualTo("New");
        assertThat(existing.getLastName()).isEqualTo("AmirAdilzhanAishaPatient");
        assertThat(existing.getAge()).isEqualTo(31);
        assertThat(existing.getIllness()).isEqualTo("healthy");
        assertThat(updated).isEqualTo(response);
    }

    @Test
    void deletePatientUsesExceptionFlowWhenMissing() {
        when(patientRepository.existsById(42L)).thenReturn(false);

        assertThatThrownBy(() -> patientService.deletePatient(42L))
                .isInstanceOf(AmirAdilzhanAishaPatientNotFoundException.class);
        verify(patientRepository, never()).deleteById(42L);
    }

    @Test
    void deletePatientRemovesExistingPatient() {
        when(patientRepository.existsById(5L)).thenReturn(true);

        patientService.deletePatient(5L);

        verify(patientRepository).deleteById(5L);
    }

    @SuppressWarnings("unchecked")
    private Specification<AmirAdilzhanAishaPatient> anySpecification() {
        return any(Specification.class);
    }

    private AmirAdilzhanAishaPatient AmirAdilzhanAishaPatient(Long id, String firstName, String lastName, int age, String illness) {
        AmirAdilzhanAishaPatient AmirAdilzhanAishaPatient = new AmirAdilzhanAishaPatient(firstName, lastName, age, illness);
        AmirAdilzhanAishaPatient.setId(id);
        return AmirAdilzhanAishaPatient;
    }
}
