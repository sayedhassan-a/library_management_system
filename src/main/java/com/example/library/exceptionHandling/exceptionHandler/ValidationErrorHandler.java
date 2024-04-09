package com.example.library.exceptionHandling.exceptionHandler;

import com.example.library.exceptionHandling.errorResponse.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ValidationResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exc) {
        ValidationResponse error = new ValidationResponse();
        Map<String, String> errors = new HashMap<>();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        exc.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        error.setTimeStamp(LocalDateTime.now());
        error.setErrors(errors);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
