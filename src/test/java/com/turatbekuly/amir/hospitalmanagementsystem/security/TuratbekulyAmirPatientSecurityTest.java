package com.turatbekuly.amir.hospitalmanagementsystem.security;

import com.turatbekuly.amir.hospitalmanagementsystem.config.TuratbekulyAmirSecurityConfig;
import com.turatbekuly.amir.hospitalmanagementsystem.controller.TuratbekulyAmirPatientController;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirPagedResponseDto;
import com.turatbekuly.amir.hospitalmanagementsystem.service.TuratbekulyAmirPatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TuratbekulyAmirPatientController.class)
@Import({
        TuratbekulyAmirSecurityConfig.class,
        TuratbekulyAmirAuthenticationEntryPoint.class,
        TuratbekulyAmirAccessDeniedHandler.class,
        TuratbekulyAmirJwtAuthenticationFilter.class
})
class TuratbekulyAmirPatientSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TuratbekulyAmirPatientService patientService;

    @MockBean
    private TuratbekulyAmirJwtService jwtService;

    @MockBean
    private TuratbekulyAmirUserDetailsService userDetailsService;

    @Test
    void anonymousUserCannotReadPatients() throws Exception {
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userCanReadPatients() throws Exception {
        when(patientService.getAllPatients(any(), any(), any(), any(), any(), any(), anyInt(), anyInt(), any(), any()))
                .thenReturn(new TuratbekulyAmirPagedResponseDto<>(List.of(), 0, 10, 0, 0, true));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userCannotDeletePatients() throws Exception {
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanDeletePatients() throws Exception {
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());
    }
}
