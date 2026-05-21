package com.turatbekuly.amir.hospitalmanagementsystem.mapper;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.*;
import com.turatbekuly.amir.hospitalmanagementsystem.entity.*;
import org.springframework.stereotype.Component;

@Component
public class AmirAdilzhanAishaEntityMapper {

    public AmirAdilzhanAishaPatientDto toPatientDto(AmirAdilzhanAishaPatient AmirAdilzhanAishaPatient) {
        return new AmirAdilzhanAishaPatientDto(
                AmirAdilzhanAishaPatient.getId(),
                AmirAdilzhanAishaPatient.getFirstName(),
                AmirAdilzhanAishaPatient.getLastName(),
                AmirAdilzhanAishaPatient.getAge(),
                AmirAdilzhanAishaPatient.getIllness()
        );
    }

    public AmirAdilzhanAishaPatient toPatientEntity(AmirAdilzhanAishaPatientDto patientDto) {
        AmirAdilzhanAishaPatient AmirAdilzhanAishaPatient = new AmirAdilzhanAishaPatient();
        AmirAdilzhanAishaPatient.setFirstName(patientDto.firstName());
        AmirAdilzhanAishaPatient.setLastName(patientDto.lastName());
        AmirAdilzhanAishaPatient.setAge(patientDto.age());
        AmirAdilzhanAishaPatient.setIllness(patientDto.illness());
        return AmirAdilzhanAishaPatient;
    }

    public AmirAdilzhanAishaDepartmentDto toDepartmentDto(AmirAdilzhanAishaDepartment AmirAdilzhanAishaDepartment) {
        return new AmirAdilzhanAishaDepartmentDto(
                AmirAdilzhanAishaDepartment.getId(),
                AmirAdilzhanAishaDepartment.getName(),
                AmirAdilzhanAishaDepartment.getDescription()
        );
    }

    public AmirAdilzhanAishaDoctorDto toDoctorDto(AmirAdilzhanAishaDoctor AmirAdilzhanAishaDoctor) {
        return new AmirAdilzhanAishaDoctorDto(
                AmirAdilzhanAishaDoctor.getId(),
                AmirAdilzhanAishaDoctor.getFirstName(),
                AmirAdilzhanAishaDoctor.getLastName(),
                AmirAdilzhanAishaDoctor.getSpecialization(),
                AmirAdilzhanAishaDoctor.getEmail(),
                AmirAdilzhanAishaDoctor.getDepartment() != null ? AmirAdilzhanAishaDoctor.getDepartment().getId() : null,
                AmirAdilzhanAishaDoctor.getDepartment() != null ? AmirAdilzhanAishaDoctor.getDepartment().getName() : null
        );
    }

    public AmirAdilzhanAishaAppointmentDto toAppointmentDto(AmirAdilzhanAishaAppointment AmirAdilzhanAishaAppointment) {
        return new AmirAdilzhanAishaAppointmentDto(
                AmirAdilzhanAishaAppointment.getId(),
                AmirAdilzhanAishaAppointment.getAppointmentDateTime(),
                AmirAdilzhanAishaAppointment.getStatus(),
                AmirAdilzhanAishaAppointment.getNotes(),
                AmirAdilzhanAishaAppointment.getPatient() != null ? AmirAdilzhanAishaAppointment.getPatient().getId() : null,
                AmirAdilzhanAishaAppointment.getDoctor() != null ? AmirAdilzhanAishaAppointment.getDoctor().getId() : null
        );
    }

    public AmirAdilzhanAishaMedicalRecordDto toMedicalRecordDto(AmirAdilzhanAishaMedicalRecord AmirAdilzhanAishaMedicalRecord) {
        return new AmirAdilzhanAishaMedicalRecordDto(
                AmirAdilzhanAishaMedicalRecord.getId(),
                AmirAdilzhanAishaMedicalRecord.getDiagnosis(),
                AmirAdilzhanAishaMedicalRecord.getTreatmentPlan(),
                AmirAdilzhanAishaMedicalRecord.getCreatedAt(),
                AmirAdilzhanAishaMedicalRecord.getPatient() != null ? AmirAdilzhanAishaMedicalRecord.getPatient().getId() : null,
                AmirAdilzhanAishaMedicalRecord.getDoctor() != null ? AmirAdilzhanAishaMedicalRecord.getDoctor().getId() : null
        );
    }

    public AmirAdilzhanAishaPrescriptionDto toPrescriptionDto(AmirAdilzhanAishaPrescription AmirAdilzhanAishaPrescription) {
        return new AmirAdilzhanAishaPrescriptionDto(
                AmirAdilzhanAishaPrescription.getId(),
                AmirAdilzhanAishaPrescription.getMedicationName(),
                AmirAdilzhanAishaPrescription.getDosage(),
                AmirAdilzhanAishaPrescription.getInstructions(),
                AmirAdilzhanAishaPrescription.getIssuedDate(),
                AmirAdilzhanAishaPrescription.getPatient() != null ? AmirAdilzhanAishaPrescription.getPatient().getId() : null,
                AmirAdilzhanAishaPrescription.getDoctor() != null ? AmirAdilzhanAishaPrescription.getDoctor().getId() : null
        );
    }

    public AmirAdilzhanAishaUserDto toUserDto(AmirAdilzhanAishaUser user) {
        return new AmirAdilzhanAishaUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().name() : null
        );
    }
}
