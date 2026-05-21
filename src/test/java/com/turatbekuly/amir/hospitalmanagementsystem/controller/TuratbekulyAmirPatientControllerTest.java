package com.turatbekuly.amir.hospitalmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.PagedResponseDto;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPatientDto;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.GlobalExceptionHandler;
import com.turatbekuly.amir.hospitalmanagementsystem.exception.TuratbekulyAmirPatientNotFoundException;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirPatientService;
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

class TuratbekulyAmirPatientControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TuratbekulyAmirPatientService patientService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        patientService = mock(TuratbekulyAmirPatientService.class);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new TuratbekulyAmirPatientController(patientService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void getAllPatientsPassesSearchFilterAndPaginationParams() throws Exception {
        TuratbekulyAmirPatientDto dto = new TuratbekulyAmirPatientDto(1L, "Amir", "Turatbekuly", 24, "flu");
        when(patientService.getAllPatients("amir", "Am", "Tur", "flu", 18, 65, 2, 5, "age", "desc"))
                .thenReturn(new PagedResponseDto<>(List.of(dto), 2, 5, 1, 1, true));

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
        TuratbekulyAmirPatientDto invalid = new TuratbekulyAmirPatientDto(null, "", "", -1, "flu");

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
        org.mockito.Mockito.doThrow(new TuratbekulyAmirPatientNotFoundException(77L))
                .when(patientService).deletePatient(eq(77L));

        mockMvc.perform(delete("/api/patients/77"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("77")))
                .andExpect(jsonPath("$.path").value("/api/patients/77"));
    }
}
