package com.turatbekuly.amir.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class AmirAdilzhanAishaAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent(message = "Дата приема должна быть текущей или будущей")
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AmirAdilzhanAishaAppointmentStatus status = AmirAdilzhanAishaAppointmentStatus.SCHEDULED;

    @Size(max = 500, message = "Комментарий к приему не должен превышать 500 символов")
    @Column(length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private AmirAdilzhanAishaPatient AmirAdilzhanAishaPatient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private AmirAdilzhanAishaDoctor AmirAdilzhanAishaDoctor;

    public AmirAdilzhanAishaAppointment() {
    }

    public AmirAdilzhanAishaAppointment(LocalDateTime appointmentDateTime, AmirAdilzhanAishaAppointmentStatus status, String notes, AmirAdilzhanAishaPatient AmirAdilzhanAishaPatient, AmirAdilzhanAishaDoctor AmirAdilzhanAishaDoctor) {
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
        this.notes = notes;
        this.AmirAdilzhanAishaPatient = AmirAdilzhanAishaPatient;
        this.AmirAdilzhanAishaDoctor = AmirAdilzhanAishaDoctor;
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

    public AmirAdilzhanAishaAppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AmirAdilzhanAishaAppointmentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public AmirAdilzhanAishaPatient getPatient() {
        return AmirAdilzhanAishaPatient;
    }

    public void setPatient(AmirAdilzhanAishaPatient AmirAdilzhanAishaPatient) {
        this.AmirAdilzhanAishaPatient = AmirAdilzhanAishaPatient;
    }

    public AmirAdilzhanAishaDoctor getDoctor() {
        return AmirAdilzhanAishaDoctor;
    }

    public void setDoctor(AmirAdilzhanAishaDoctor AmirAdilzhanAishaDoctor) {
        this.AmirAdilzhanAishaDoctor = AmirAdilzhanAishaDoctor;
    }
}
