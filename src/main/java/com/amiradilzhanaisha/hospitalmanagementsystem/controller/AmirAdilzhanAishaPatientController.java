package com.amiradilzhanaisha.hospitalmanagementsystem.controller;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPagedResponseDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaPatientDto;
import com.amiradilzhanaisha.hospitalmanagementsystem.service.AmirAdilzhanAishaPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
@Tag(name = "Patients", description = "AmirAdilzhanAishaPatient CRUD, search, filtering, sorting and pagination")
@SecurityRequirement(name = "bearerAuth")
public class AmirAdilzhanAishaPatientController {

    private final AmirAdilzhanAishaPatientService patientService;

    public AmirAdilzhanAishaPatientController(AmirAdilzhanAishaPatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get patients", description = "Returns paginated patients with search, filtering and sorting support")
    public AmirAdilzhanAishaPagedResponseDto<AmirAdilzhanAishaPatientDto> getAllPatients(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String illness,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return patientService.getAllPatients(search, firstName, lastName, illness, minAge, maxAge, page, size, sortBy, sortDir);
    }

    @PostMapping
    @Operation(summary = "Create AmirAdilzhanAishaPatient", description = "Creates a new AmirAdilzhanAishaPatient. Requires ADMIN role")
    public AmirAdilzhanAishaPatientDto createPatient(@Valid @RequestBody AmirAdilzhanAishaPatientDto patientDto) {
        return patientService.createPatient(patientDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get AmirAdilzhanAishaPatient by ID", description = "Returns one AmirAdilzhanAishaPatient by identifier")
    public ResponseEntity<AmirAdilzhanAishaPatientDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update AmirAdilzhanAishaPatient", description = "Updates AmirAdilzhanAishaPatient fields by identifier. Requires ADMIN role")
    public ResponseEntity<AmirAdilzhanAishaPatientDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody AmirAdilzhanAishaPatientDto patientDto
    ) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete AmirAdilzhanAishaPatient", description = "Deletes AmirAdilzhanAishaPatient by identifier. Requires ADMIN role")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
