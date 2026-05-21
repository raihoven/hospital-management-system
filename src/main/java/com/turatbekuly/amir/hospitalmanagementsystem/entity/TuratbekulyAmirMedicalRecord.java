package com.turatbekuly.amir.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
public class TuratbekulyAmirMedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Диагноз не должен быть пустым")
    @Size(max = 255, message = "Диагноз не должен превышать 255 символов")
    @Column(nullable = false)
    private String diagnosis;

    @Size(max = 1000, message = "План лечения не должен превышать 1000 символов")
    @Column(length = 1000)
    private String treatmentPlan;

    @PastOrPresent(message = "Дата записи не может быть из будущего")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private TuratbekulyAmirPatient TuratbekulyAmirPatient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private TuratbekulyAmirDoctor TuratbekulyAmirDoctor;

    public TuratbekulyAmirMedicalRecord() {
    }

    public TuratbekulyAmirMedicalRecord(String diagnosis, String treatmentPlan, LocalDateTime createdAt, TuratbekulyAmirPatient TuratbekulyAmirPatient, TuratbekulyAmirDoctor TuratbekulyAmirDoctor) {
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.createdAt = createdAt;
        this.TuratbekulyAmirPatient = TuratbekulyAmirPatient;
        this.TuratbekulyAmirDoctor = TuratbekulyAmirDoctor;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TuratbekulyAmirPatient getPatient() {
        return TuratbekulyAmirPatient;
    }

    public void setPatient(TuratbekulyAmirPatient TuratbekulyAmirPatient) {
        this.TuratbekulyAmirPatient = TuratbekulyAmirPatient;
    }

    public TuratbekulyAmirDoctor getDoctor() {
        return TuratbekulyAmirDoctor;
    }

    public void setDoctor(TuratbekulyAmirDoctor TuratbekulyAmirDoctor) {
        this.TuratbekulyAmirDoctor = TuratbekulyAmirDoctor;
    }
}
