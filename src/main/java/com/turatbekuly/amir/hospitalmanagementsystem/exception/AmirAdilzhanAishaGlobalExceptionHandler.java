package com.turatbekuly.amir.hospitalmanagementsystem.exception;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaApiErrorResponse;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaApiValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class AmirAdilzhanAishaGlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AmirAdilzhanAishaGlobalExceptionHandler.class);

    @ExceptionHandler(AmirAdilzhanAishaPatientNotFoundException.class)
    public ResponseEntity<AmirAdilzhanAishaApiErrorResponse> handlePatientNotFound(
            AmirAdilzhanAishaPatientNotFoundException exception,
            HttpServletRequest request
    ) {
        log.warn("AmirAdilzhanAishaPatient not found: path={} message={}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(AmirAdilzhanAishaFileNotFoundException.class)
    public ResponseEntity<AmirAdilzhanAishaApiErrorResponse> handleFileNotFound(
            AmirAdilzhanAishaFileNotFoundException exception,
            HttpServletRequest request
    ) {
        log.warn("File not found: path={} message={}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(AmirAdilzhanAishaFileStorageException.class)
    public ResponseEntity<AmirAdilzhanAishaApiErrorResponse> handleFileStorageError(
            AmirAdilzhanAishaFileStorageException exception,
            HttpServletRequest request
    ) {
        log.error("File storage error: path={} message={}", request.getRequestURI(), exception.getMessage(), exception);
        return ResponseEntity.badRequest()
                .body(buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(AmirAdilzhanAishaUserAlreadyExistsException.class)
    public ResponseEntity<AmirAdilzhanAishaApiErrorResponse> handleUserAlreadyExists(
            AmirAdilzhanAishaUserAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        log.warn("User already exists: path={} message={}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AmirAdilzhanAishaApiErrorResponse> handleBadCredentials(
            BadCredentialsException exception,
            HttpServletRequest request
    ) {
        log.warn("Authentication failed: path={} message={}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResponse(HttpStatus.UNAUTHORIZED, "Неверный email или пароль", request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AmirAdilzhanAishaApiValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.warn("Validation failed: path={} errors={}", request.getRequestURI(), validationErrors);

        AmirAdilzhanAishaApiValidationErrorResponse errorResponse = new AmirAdilzhanAishaApiValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Ошибка валидации входных данных",
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AmirAdilzhanAishaApiValidationErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        exception.getConstraintViolations().forEach(violation ->
                validationErrors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        log.warn("Constraint violation: path={} errors={}", request.getRequestURI(), validationErrors);

        AmirAdilzhanAishaApiValidationErrorResponse errorResponse = new AmirAdilzhanAishaApiValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Ошибка ограничений валидации",
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestPartException.class,
            MaxUploadSizeExceededException.class
    })
    public ResponseEntity<AmirAdilzhanAishaApiErrorResponse> handleRequestErrors(
            Exception exception,
            HttpServletRequest request
    ) {
        log.warn("Request error: path={} message={}", request.getRequestURI(), exception.getMessage());

        String message = exception instanceof MaxUploadSizeExceededException
                ? "Превышен максимально допустимый размер загружаемого файла"
                : "Некорректные параметры запроса";

        return ResponseEntity.badRequest()
                .body(buildErrorResponse(HttpStatus.BAD_REQUEST, message, request.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AmirAdilzhanAishaApiErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        log.error("Database integrity error: path={} message={}", request.getRequestURI(), exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(HttpStatus.CONFLICT, "Операция нарушает ограничения базы данных", request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AmirAdilzhanAishaApiErrorResponse> handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {
        log.error("Unexpected error: path={} message={}", request.getRequestURI(), exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка сервера", request.getRequestURI()));
    }

    private AmirAdilzhanAishaApiErrorResponse buildErrorResponse(HttpStatus status, String message, String path) {
        return new AmirAdilzhanAishaApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    }
}
