package com.amiradilzhanaisha.hospitalmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPagedResponseDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.exception.AmirAdilzhanAishaGlobalExceptionHandler;
import com.amiradilzhanaisha.hospitalmanagementsystem.exception.AmirAdilzhanAishaPatientNotFoundException;
import com.amiradilzhanaisha.hospitalmanagementsystem.service.AmirAdilzhanAishaPatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AmirAdilzhanAishaPatientControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private AmirAdilzhanAishaPatientService patientService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        patientService = mock(AmirAdilzhanAishaPatientService.class);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new AmirAdilzhanAishaPatientController(patientService))
                .setControllerAdvice(new AmirAdilzhanAishaGlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void getAllPatientsPassesSearchFilterAndPaginationParams() throws Exception {
        AmirAdilzhanAishaPatientDto dto = new AmirAdilzhanAishaPatientDto(1L, "Amir", "AdilzhanAisha", 24, "flu");
        when(patientService.getAllPatients("amir", "Am", "Tur", "flu", 18, 65, 2, 5, "age", "desc"))
                .thenReturn(new AmirAdilzhanAishaPagedResponseDto<>(List.of(dto), 2, 5, 1, 1, true));

        mockMvc.perform(get("/api/patients")
                        .param("search", "amir")
                        .param("firstName", "Am")
                        .param("lastName", "Tur")
                        .param("illness", "flu")
                        .param("minAge", "18")
                        .param("maxAge", "65")
                        .param("page", "2")
                        .param("size", "5")
                        .param("sortBy", "age")
                        .param("sortDir", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Amir"))
                .andExpect(jsonPath("$.page").value(2))
                .andExpect(jsonPath("$.size").value(5));

        verify(patientService).getAllPatients("amir", "Am", "Tur", "flu", 18, 65, 2, 5, "age", "desc");
    }

    @Test
    void createPatientRejectsInvalidPayload() throws Exception {
        AmirAdilzhanAishaPatientDto invalid = new AmirAdilzhanAishaPatientDto(null, "", "", -1, "flu");

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.firstName").exists())
                .andExpect(jsonPath("$.validationErrors.age").exists());
    }

    @Test
    void deletePatientReturnsNoContentForExistingPatient() throws Exception {
        mockMvc.perform(delete("/api/patients/3"))
                .andExpect(status().isNoContent());

        verify(patientService).deletePatient(3L);
    }

    @Test
    void deletePatientUsesGlobalExceptionHandlerForMissingPatient() throws Exception {
        org.mockito.Mockito.doThrow(new AmirAdilzhanAishaPatientNotFoundException(77L))
                .when(patientService).deletePatient(eq(77L));

        mockMvc.perform(delete("/api/patients/77"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("77")))
                .andExpect(jsonPath("$.path").value("/api/patients/77"));
    }
}
