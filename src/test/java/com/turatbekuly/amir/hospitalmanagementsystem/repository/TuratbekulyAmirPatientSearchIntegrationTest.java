package com.turatbekuly.amir.hospitalmanagementsystem.repository;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.PagedResponseDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPatientDto;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.Patient;
import com.turatbekuly.amir.hospitalmanagementsystem.mapper.TuratbekulyAmirEntityMapper;
import com.turatbekuly.amir.hospitalmanagementsystem.service.impl.TuratbekulyAmirPatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@Import({TuratbekulyAmirPatientServiceImpl.class, TuratbekulyAmirEntityMapper.class})
class TuratbekulyAmirPatientSearchIntegrationTest {

    @Autowired
    private TuratbekulyAmirPatientRepository patientRepository;

    @Autowired
    private TuratbekulyAmirPatientServiceImpl patientService;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
        patientRepository.save(new Patient("Amir", "Turatbekuly", 24, "cardiology follow-up"));
        patientRepository.save(new Patient("Madina", "Aidarova", 31, "neurology"));
        patientRepository.save(new Patient("Aidar", "Sultanov", 45, "cardiology diagnostics"));
    }

    @Test
    void searchFindsAcrossNameAndIllnessFields() {
        PagedResponseDto<TuratbekulyAmirPatientDto> response = patientService.getAllPatients(
                "cardiology", null, null, null, null, null, 0, 10, "id", "asc"
        );

        assertThat(response.content())
                .extracting(TuratbekulyAmirPatientDto::lastName)
                .containsExactly("Turatbekuly", "Sultanov");
    }

    @Test
    void filtersByNamesIllnessAndAgeRange() {
        PagedResponseDto<TuratbekulyAmirPatientDto> response = patientService.getAllPatients(
                null, "aid", null, "cardiology", 40, 50, 0, 10, "age", "desc"
        );

        assertThat(response.content()).hasSize(1);
        assertThat(response.content().get(0).lastName()).isEqualTo("Sultanov");
    }

    @Test
    void paginatesAndSortsResults() {
        PagedResponseDto<TuratbekulyAmirPatientDto> response = patientService.getAllPatients(
                null, null, null, null, null, null, 0, 2, "age", "desc"
        );

        assertThat(response.content())
                .extracting(TuratbekulyAmirPatientDto::firstName)
                .containsExactly("Aidar", "Madina");
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.totalElements()).isEqualTo(3);
        assertThat(response.last()).isFalse();
    }
}
