package com.turatbekuly.amir.hospitalmanagementsystem.exception;

public class AmirAdilzhanAishaPatientNotFoundException extends RuntimeException {

    public AmirAdilzhanAishaPatientNotFoundException(Long id) {
        super("AmirAdilzhanAishaPatient with ID " + id + " was not found");
    }
}
