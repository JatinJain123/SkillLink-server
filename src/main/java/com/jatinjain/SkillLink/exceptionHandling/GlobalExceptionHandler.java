package com.jatinjain.SkillLink.exceptionHandling;

import com.jatinjain.SkillLink.exceptionHandling.exceptions.UserNotRegisteredException;
import com.jatinjain.SkillLink.exceptionHandling.exceptions.WrongCredentialsException;
import com.jatinjain.SkillLink.model.DTOs.auth.AuthResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AuthResponse> handleException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse(false, e.getMessage(), null, null));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<AuthResponse> handleException(DuplicateKeyException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new AuthResponse(false, e.getMessage(), null, null));
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<AuthResponse> handleException(WrongCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse(false, e.getMessage(), null, null));
    }

    @ExceptionHandler(UserNotRegisteredException.class)
    public ResponseEntity<AuthResponse> handleException(UserNotRegisteredException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new AuthResponse(false, e.getMessage(), null, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponse(false, "unexpected error occur, try after some time", null, null));
    }
}
