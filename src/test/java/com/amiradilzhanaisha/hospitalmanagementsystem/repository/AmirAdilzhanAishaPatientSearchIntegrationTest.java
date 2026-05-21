package com.amiradilzhanaisha.hospitalmanagementsystem.repository;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPagedResponseDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.entity.AmirAdilzhanAishaPatient;
import com.amiradilzhanaisha.hospitalmanagementsystem.mapper.AmirAdilzhanAishaEntityMapper;
import com.amiradilzhanaisha.hospitalmanagementsystem.service.impl.AmirAdilzhanAishaPatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@Import({AmirAdilzhanAishaPatientServiceImpl.class, AmirAdilzhanAishaEntityMapper.class})
class AmirAdilzhanAishaPatientSearchIntegrationTest {

    @Autowired
    private AmirAdilzhanAishaPatientRepository patientRepository;

    @Autowired
    private AmirAdilzhanAishaPatientServiceImpl patientService;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
        patientRepository.save(new AmirAdilzhanAishaPatient("Amir", "AdilzhanAisha", 24, "cardiology follow-up"));
        patientRepository.save(new AmirAdilzhanAishaPatient("Madina", "Aidarova", 31, "neurology"));
        patientRepository.save(new AmirAdilzhanAishaPatient("Aidar", "Sultanov", 45, "cardiology diagnostics"));
    }

    @Test
    void searchFindsAcrossNameAndIllnessFields() {
        AmirAdilzhanAishaPagedResponseDto<AmirAdilzhanAishaPatientDto> response = patientService.getAllPatients(
                "cardiology", null, null, null, null, null, 0, 10, "id", "asc"
        );

        assertThat(response.content())
                .extracting(AmirAdilzhanAishaPatientDto::lastName)
                .containsExactly("AdilzhanAisha", "Sultanov");
    }

    @Test
    void filtersByNamesIllnessAndAgeRange() {
        AmirAdilzhanAishaPagedResponseDto<AmirAdilzhanAishaPatientDto> response = patientService.getAllPatients(
                null, "aid", null, "cardiology", 40, 50, 0, 10, "age", "desc"
        );

        assertThat(response.content()).hasSize(1);
        assertThat(response.content().get(0).lastName()).isEqualTo("Sultanov");
    }

    @Test
    void paginatesAndSortsResults() {
        AmirAdilzhanAishaPagedResponseDto<AmirAdilzhanAishaPatientDto> response = patientService.getAllPatients(
                null, null, null, null, null, null, 0, 2, "age", "desc"
        );

        assertThat(response.content())
                .extracting(AmirAdilzhanAishaPatientDto::firstName)
                .containsExactly("Aidar", "Madina");
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.totalElements()).isEqualTo(3);
        assertThat(response.last()).isFalse();
    }
}
