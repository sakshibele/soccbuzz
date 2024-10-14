package com.pratiti.soccbuzz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MatchNotFoundException.class})
    public ResponseEntity<Object> handleMatchNotFoundException(MatchNotFoundException exception){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
