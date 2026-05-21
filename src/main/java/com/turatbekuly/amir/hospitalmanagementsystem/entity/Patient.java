package com.turatbekuly.amir.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Фамилия не должна быть пустой")
    @Column(nullable = false)
    private String lastName;

    @Positive(message = "Возраст должен быть больше 0")
    private int age;

    private String illness;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<Prescription> prescriptions = new ArrayList<>();

    // Пустой конструктор (обязателен для Spring и JPA)
    public Patient() {
    }

    // Конструктор со всеми параметрами (кроме id)
    public Patient(String firstName, String lastName, int age, String illness) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.illness = illness;
    }

    // --- Геттеры и Сеттеры ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getIllness() { return illness; }
    public void setIllness(String illness) { this.illness = illness; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }

    public List<MedicalRecord> getMedicalRecords() { return medicalRecords; }
    public void setMedicalRecords(List<MedicalRecord> medicalRecords) { this.medicalRecords = medicalRecords; }

    public List<Prescription> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(List<Prescription> prescriptions) { this.prescriptions = prescriptions; }
}
