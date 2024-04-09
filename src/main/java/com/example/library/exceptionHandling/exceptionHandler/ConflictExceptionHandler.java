package com.example.library.exceptionHandling.exceptionHandler;

import com.example.library.exceptionHandling.errorResponse.ErrorResponse;
import com.example.library.exceptionHandling.exception.ConflictException;
import com.example.library.exceptionHandling.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ConflictExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ConflictException exc) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
