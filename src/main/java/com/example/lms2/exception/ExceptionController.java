package com.example.lms2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)

    public ResponseEntity<Object> handleNodataFoundException(
            CustomException ex) {
        ErrorMessage exceptionResponse = new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now().toString(),
                ex.getMessage(),
                "");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
