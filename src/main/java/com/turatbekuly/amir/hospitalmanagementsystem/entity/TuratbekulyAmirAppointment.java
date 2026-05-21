package com.turatbekuly.amir.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class TuratbekulyAmirAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent(message = "Дата приема должна быть текущей или будущей")
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TuratbekulyAmirAppointmentStatus status = TuratbekulyAmirAppointmentStatus.SCHEDULED;

    @Size(max = 500, message = "Комментарий к приему не должен превышать 500 символов")
    @Column(length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private TuratbekulyAmirPatient TuratbekulyAmirPatient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private TuratbekulyAmirDoctor TuratbekulyAmirDoctor;

    public TuratbekulyAmirAppointment() {
    }

    public TuratbekulyAmirAppointment(LocalDateTime appointmentDateTime, TuratbekulyAmirAppointmentStatus status, String notes, TuratbekulyAmirPatient TuratbekulyAmirPatient, TuratbekulyAmirDoctor TuratbekulyAmirDoctor) {
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
        this.notes = notes;
        this.TuratbekulyAmirPatient = TuratbekulyAmirPatient;
        this.TuratbekulyAmirDoctor = TuratbekulyAmirDoctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public TuratbekulyAmirAppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(TuratbekulyAmirAppointmentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
