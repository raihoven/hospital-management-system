package com.turatbekuly.amir.hospitalmanagementsystem.mapper;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.*;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.*;
import org.springframework.stereotype.Component;

@Component
public class TuratbekulyAmirEntityMapper {

    public TuratbekulyAmirPatientDto toPatientDto(TuratbekulyAmirPatient TuratbekulyAmirPatient) {
        return new TuratbekulyAmirPatientDto(
                TuratbekulyAmirPatient.getId(),
                TuratbekulyAmirPatient.getFirstName(),
                TuratbekulyAmirPatient.getLastName(),
                TuratbekulyAmirPatient.getAge(),
                TuratbekulyAmirPatient.getIllness()
        );
    }

    public TuratbekulyAmirPatient toPatientEntity(TuratbekulyAmirPatientDto patientDto) {
        TuratbekulyAmirPatient TuratbekulyAmirPatient = new TuratbekulyAmirPatient();
        TuratbekulyAmirPatient.setFirstName(patientDto.firstName());
        TuratbekulyAmirPatient.setLastName(patientDto.lastName());
        TuratbekulyAmirPatient.setAge(patientDto.age());
        TuratbekulyAmirPatient.setIllness(patientDto.illness());
        return TuratbekulyAmirPatient;
    }

    public TuratbekulyAmirDepartmentDto toDepartmentDto(TuratbekulyAmirDepartment TuratbekulyAmirDepartment) {
        return new TuratbekulyAmirDepartmentDto(
                TuratbekulyAmirDepartment.getId(),
                TuratbekulyAmirDepartment.getName(),
                TuratbekulyAmirDepartment.getDescription()
        );
    }

    public TuratbekulyAmirDoctorDto toDoctorDto(TuratbekulyAmirDoctor TuratbekulyAmirDoctor) {
        return new TuratbekulyAmirDoctorDto(
                TuratbekulyAmirDoctor.getId(),
                TuratbekulyAmirDoctor.getFirstName(),
                TuratbekulyAmirDoctor.getLastName(),
                TuratbekulyAmirDoctor.getSpecialization(),
                TuratbekulyAmirDoctor.getEmail(),
                TuratbekulyAmirDoctor.getDepartment() != null ? TuratbekulyAmirDoctor.getDepartment().getId() : null,
                TuratbekulyAmirDoctor.getDepartment() != null ? TuratbekulyAmirDoctor.getDepartment().getName() : null
        );
    }

    public TuratbekulyAmirAppointmentDto toAppointmentDto(TuratbekulyAmirAppointment TuratbekulyAmirAppointment) {
        return new TuratbekulyAmirAppointmentDto(
                TuratbekulyAmirAppointment.getId(),
                TuratbekulyAmirAppointment.getAppointmentDateTime(),
                TuratbekulyAmirAppointment.getStatus(),
                TuratbekulyAmirAppointment.getNotes(),
                TuratbekulyAmirAppointment.getPatient() != null ? TuratbekulyAmirAppointment.getPatient().getId() : null,
                TuratbekulyAmirAppointment.getDoctor() != null ? TuratbekulyAmirAppointment.getDoctor().getId() : null
        );
    }

    public TuratbekulyAmirMedicalRecordDto toMedicalRecordDto(TuratbekulyAmirMedicalRecord TuratbekulyAmirMedicalRecord) {
        return new TuratbekulyAmirMedicalRecordDto(
                TuratbekulyAmirMedicalRecord.getId(),
                TuratbekulyAmirMedicalRecord.getDiagnosis(),
                TuratbekulyAmirMedicalRecord.getTreatmentPlan(),
                TuratbekulyAmirMedicalRecord.getCreatedAt(),
                TuratbekulyAmirMedicalRecord.getPatient() != null ? TuratbekulyAmirMedicalRecord.getPatient().getId() : null,
                TuratbekulyAmirMedicalRecord.getDoctor() != null ? TuratbekulyAmirMedicalRecord.getDoctor().getId() : null
        );
    }

    public TuratbekulyAmirPrescriptionDto toPrescriptionDto(TuratbekulyAmirPrescription TuratbekulyAmirPrescription) {
        return new TuratbekulyAmirPrescriptionDto(
                TuratbekulyAmirPrescription.getId(),
                TuratbekulyAmirPrescription.getMedicationName(),
                TuratbekulyAmirPrescription.getDosage(),
                TuratbekulyAmirPrescription.getInstructions(),
                TuratbekulyAmirPrescription.getIssuedDate(),
                TuratbekulyAmirPrescription.getPatient() != null ? TuratbekulyAmirPrescription.getPatient().getId() : null,
                TuratbekulyAmirPrescription.getDoctor() != null ? TuratbekulyAmirPrescription.getDoctor().getId() : null
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
