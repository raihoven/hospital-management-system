package com.turatbekuly.amir.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название лекарства не должно быть пустым")
    @Size(max = 100, message = "Название лекарства не должно превышать 100 символов")
    @Column(nullable = false, length = 100)
    private String medicationName;

    @NotBlank(message = "Дозировка не должна быть пустой")
    @Size(max = 100, message = "Дозировка не должна превышать 100 символов")
    @Column(nullable = false, length = 100)
    private String dosage;

    @Size(max = 500, message = "Инструкция не должна превышать 500 символов")
    @Column(length = 500)
    private String instructions;

    @PastOrPresent(message = "Дата назначения не может быть из будущего")
    @Column(nullable = false)
    private LocalDate issuedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    public Prescription() {
    }

    public Prescription(String medicationName, String dosage, String instructions, LocalDate issuedDate, Patient patient, Doctor doctor) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.instructions = instructions;
        this.issuedDate = issuedDate;
        this.patient = patient;
        this.doctor = doctor;
    }

    @PrePersist
    public void prePersist() {
        if (issuedDate == null) {
            issuedDate = LocalDate.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
