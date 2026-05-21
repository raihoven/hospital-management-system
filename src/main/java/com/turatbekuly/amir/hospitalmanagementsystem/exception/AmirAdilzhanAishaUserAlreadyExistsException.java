package com.turatbekuly.amir.hospitalmanagementsystem.exception;

public class AmirAdilzhanAishaUserAlreadyExistsException extends RuntimeException {

    public AmirAdilzhanAishaUserAlreadyExistsException(String email) {
        super("Пользователь с email " + email + " уже существует");
    }
}
