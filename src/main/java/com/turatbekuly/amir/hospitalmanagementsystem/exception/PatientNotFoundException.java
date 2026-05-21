package com.turatbekuly.amir.hospitalmanagementsystem.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Long id) {
        super("Пациент с ID " + id + " не найден");
    }
}
