package com.turatbekuly.amir.hospitalmanagementsystem.exception;

public class TuratbekulyAmirUserAlreadyExistsException extends RuntimeException {

    public TuratbekulyAmirUserAlreadyExistsException(String email) {
        super("Пользователь с email " + email + " уже существует");
    }
}
