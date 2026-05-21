package com.turatbekuly.amir.hospitalmanagementsystem.exception;

public class TuratbekulyAmirFileNotFoundException extends RuntimeException {

    public TuratbekulyAmirFileNotFoundException(Long id) {
        super("Файл с ID " + id + " не найден");
    }
}
