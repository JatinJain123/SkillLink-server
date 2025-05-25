package com.jatinjain.SkillLink.controllers;

import com.jatinjain.SkillLink.services.AuthService;
import com.jatinjain.SkillLink.models.userReponses.AuthResponse;
import com.jatinjain.SkillLink.models.userRequests.AuthRequest;
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
    public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = authService.addUser(request);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );
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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = authService.validateUser(request);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
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
                    new AuthResponse(false, e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }
}
