package com.turatbekuly.amir.hospitalmanagementsystem.exception;

public class TuratbekulyAmirPatientNotFoundException extends RuntimeException {

    public TuratbekulyAmirPatientNotFoundException(Long id) {
        super("Patient with ID " + id + " was not found");
    }
}
