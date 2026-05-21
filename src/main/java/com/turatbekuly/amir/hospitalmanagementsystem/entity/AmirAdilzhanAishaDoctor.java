package com.turatbekuly.amir.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class AmirAdilzhanAishaDoctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя врача не должно быть пустым")
    @Size(max = 50, message = "Имя врача не должно превышать 50 символов")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Фамилия врача не должна быть пустой")
    @Size(max = 50, message = "Фамилия врача не должна превышать 50 символов")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Специализация не должна быть пустой")
    @Size(max = 100, message = "Специализация не должна превышать 100 символов")
    @Column(nullable = false, length = 100)
    private String specialization;

    @Email(message = "Email врача должен быть корректным")
    @Column(unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private AmirAdilzhanAishaDepartment AmirAdilzhanAishaDepartment;

    @OneToMany(mappedBy = "AmirAdilzhanAishaDoctor")
    private List<AmirAdilzhanAishaAppointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "AmirAdilzhanAishaDoctor")
    private List<AmirAdilzhanAishaMedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "AmirAdilzhanAishaDoctor")
    private List<AmirAdilzhanAishaPrescription> prescriptions = new ArrayList<>();

    public AmirAdilzhanAishaDoctor() {
    }

    public AmirAdilzhanAishaDoctor(String firstName, String lastName, String specialization, String email, AmirAdilzhanAishaDepartment AmirAdilzhanAishaDepartment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.email = email;
        this.AmirAdilzhanAishaDepartment = AmirAdilzhanAishaDepartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AmirAdilzhanAishaDepartment getDepartment() {
        return AmirAdilzhanAishaDepartment;
    }

    public void setDepartment(AmirAdilzhanAishaDepartment AmirAdilzhanAishaDepartment) {
        this.AmirAdilzhanAishaDepartment = AmirAdilzhanAishaDepartment;
    }

    public List<AmirAdilzhanAishaAppointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AmirAdilzhanAishaAppointment> appointments) {
        this.appointments = appointments;
    }

    public List<AmirAdilzhanAishaMedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<AmirAdilzhanAishaMedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<AmirAdilzhanAishaPrescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<AmirAdilzhanAishaPrescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
