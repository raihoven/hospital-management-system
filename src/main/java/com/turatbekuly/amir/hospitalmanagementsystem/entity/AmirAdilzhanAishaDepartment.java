package com.turatbekuly.amir.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class AmirAdilzhanAishaDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название отделения не должно быть пустым")
    @Size(max = 100, message = "Название отделения не должно превышать 100 символов")
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Size(max = 500, message = "Описание отделения не должно превышать 500 символов")
    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "AmirAdilzhanAishaDepartment")
    private List<AmirAdilzhanAishaDoctor> doctors = new ArrayList<>();

    public AmirAdilzhanAishaDepartment() {
    }

    public AmirAdilzhanAishaDepartment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AmirAdilzhanAishaDoctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<AmirAdilzhanAishaDoctor> doctors) {
        this.doctors = doctors;
    }
}
