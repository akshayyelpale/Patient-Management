/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.MethodArgumentNotValidException
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 */
package com.pm.patientservice.exception;

import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        HashMap errors = new HashMap();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value={EmailAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.warn("Email addresss already exist {}", (Object)ex.getMessage());
        HashMap<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Email addres already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value={PatientNotFoundException.class})
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {
        log.warn("Patient not found {}", (Object)ex.getMessage());
        HashMap<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Patient not found");
        return ResponseEntity.badRequest().body(errors);
    }
}
