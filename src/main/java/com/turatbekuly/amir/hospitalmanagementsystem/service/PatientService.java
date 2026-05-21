package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.entity.Patient;
import jakarta.validation.Valid;

import java.util.List;

public interface PatientService {

    List<Patient> getAllPatients();

    Patient createPatient(@Valid Patient patient);

    Patient getPatientById(Long id);

    boolean deletePatient(Long id);
}
