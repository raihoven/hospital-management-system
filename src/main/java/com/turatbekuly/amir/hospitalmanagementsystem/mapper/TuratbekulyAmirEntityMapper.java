package com.turatbekuly.amir.hospitalmanagementsystem.mapper;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.*;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.*;
import org.springframework.stereotype.Component;

@Component
public class TuratbekulyAmirEntityMapper {

    public PatientDto toPatientDto(Patient patient) {
        return new PatientDto(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getAge(),
                patient.getIllness()
        );
    }

    public Patient toPatientEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientDto.firstName());
        patient.setLastName(patientDto.lastName());
        patient.setAge(patientDto.age());
        patient.setIllness(patientDto.illness());
        return patient;
    }

    public DepartmentDto toDepartmentDto(Department department) {
        return new DepartmentDto(
                department.getId(),
                department.getName(),
                department.getDescription()
        );
    }

    public DoctorDto toDoctorDto(Doctor doctor) {
        return new DoctorDto(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialization(),
                doctor.getEmail(),
                doctor.getDepartment() != null ? doctor.getDepartment().getId() : null,
                doctor.getDepartment() != null ? doctor.getDepartment().getName() : null
        );
    }

    public AppointmentDto toAppointmentDto(Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getAppointmentDateTime(),
                appointment.getStatus(),
                appointment.getNotes(),
                appointment.getPatient() != null ? appointment.getPatient().getId() : null,
                appointment.getDoctor() != null ? appointment.getDoctor().getId() : null
        );
    }

    public MedicalRecordDto toMedicalRecordDto(MedicalRecord medicalRecord) {
        return new MedicalRecordDto(
                medicalRecord.getId(),
                medicalRecord.getDiagnosis(),
                medicalRecord.getTreatmentPlan(),
                medicalRecord.getCreatedAt(),
                medicalRecord.getPatient() != null ? medicalRecord.getPatient().getId() : null,
                medicalRecord.getDoctor() != null ? medicalRecord.getDoctor().getId() : null
        );
    }

    public PrescriptionDto toPrescriptionDto(Prescription prescription) {
        return new PrescriptionDto(
                prescription.getId(),
                prescription.getMedicationName(),
                prescription.getDosage(),
                prescription.getInstructions(),
                prescription.getIssuedDate(),
                prescription.getPatient() != null ? prescription.getPatient().getId() : null,
                prescription.getDoctor() != null ? prescription.getDoctor().getId() : null
        );
    }

    public TuratbekulyAmirUserDto toUserDto(TuratbekulyAmirUser user) {
        return new TuratbekulyAmirUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().name() : null
        );
    }
}
