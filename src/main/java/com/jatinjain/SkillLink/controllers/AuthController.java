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
            String userId = authService.addUser(request);
            return new ResponseEntity<>(
                    new AuthResponse(userId),
                    HttpStatus.OK
            );
        } catch(DuplicateKeyException e) {
            return new ResponseEntity<>(
                    new AuthResponse(e.getMessage()),
                    HttpStatus.CONFLICT
            );
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new AuthResponse(e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new AuthResponse(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AuthResponse("unexpected error occurred: "+ e.getMessage()),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            String status = authService.validateUser(request);
            return new ResponseEntity<>(
                    new AuthResponse(status),
                    HttpStatus.OK
            );
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new AuthResponse(e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new AuthResponse(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AuthResponse("unexpected error occurred: "+ e.getMessage()),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }
}
