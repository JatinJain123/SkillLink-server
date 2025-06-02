package com.jatinjain.SkillLink.controllers;

import com.jatinjain.SkillLink.models.userRequests.auth.SetSecretPinRequest;
import com.jatinjain.SkillLink.models.userRequests.auth.SetUsernameRequest;
import com.jatinjain.SkillLink.services.AuthService;
import com.jatinjain.SkillLink.models.userReponses.AuthResponse;
import com.jatinjain.SkillLink.models.userRequests.auth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<AuthResponse> signup(@RequestBody LoginRequest request) {
        try {
            AuthResponse authResponse = authService.addUser(request);
            return ResponseEntity.ok(authResponse);
        } catch(DuplicateKeyException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.CONFLICT
            );
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, "unexpected error occurred: "+ e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse authResponse = authService.validateUser(request);
            return ResponseEntity.ok(authResponse);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @PostMapping("/setUsername")
    @CrossOrigin
    public ResponseEntity<AuthResponse> setUsername(@RequestBody SetUsernameRequest request) {
        try {
            AuthResponse authResponse = authService.setUsername(request);
            return ResponseEntity.ok(authResponse);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @PostMapping("/setSecretPin")
    @CrossOrigin
    public ResponseEntity<AuthResponse> setSecretPin(@RequestBody SetSecretPinRequest request) {
        try {
            AuthResponse authResponse = authService.setSecretPin(request);
            return ResponseEntity.ok(authResponse);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }
}
