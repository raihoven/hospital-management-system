package com.amiradilzhanaisha.hospitalmanagementsystem.exception;

public class AmirAdilzhanAishaFileNotFoundException extends RuntimeException {

    public AmirAdilzhanAishaFileNotFoundException(Long id) {
        super("Файл с ID " + id + " не найден");
    }
}
