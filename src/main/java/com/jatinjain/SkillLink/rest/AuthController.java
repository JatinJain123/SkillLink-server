package com.jatinjain.SkillLink.rest;

import com.jatinjain.SkillLink.model.DTOs.auth.SetSecretPinRequest;
import com.jatinjain.SkillLink.model.DTOs.auth.SetUsernameRequest;
import com.jatinjain.SkillLink.service.AuthService;
import com.jatinjain.SkillLink.model.DTOs.auth.AuthResponse;
import com.jatinjain.SkillLink.model.DTOs.auth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    final private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.signup(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/user/username")
    public ResponseEntity<AuthResponse> setUsername(@RequestBody SetUsernameRequest request) {
        AuthResponse authResponse = authService.setUsername(request);
        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/user/secret-pin")
    public ResponseEntity<AuthResponse> setSecretPin(@RequestBody SetSecretPinRequest request) {
        AuthResponse authResponse = authService.setSecretPin(request);
        return ResponseEntity.ok(authResponse);
    }
}
