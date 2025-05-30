package com.jatinjain.SkillLink.controllers;

import com.jatinjain.SkillLink.models.userRequests.auth.SetSecretPinRequest;
import com.jatinjain.SkillLink.models.userRequests.auth.SetUsernameRequest;
import com.jatinjain.SkillLink.services.AuthService;
import com.jatinjain.SkillLink.models.userReponses.Response;
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
    public ResponseEntity<Response> signup(@RequestBody LoginRequest request) {
        try {
            Response response = authService.addUser(request);
            return ResponseEntity.ok(response);
        } catch(DuplicateKeyException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.CONFLICT
            );
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(false, "unexpected error occurred: "+ e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<Response> login(@RequestBody LoginRequest request) {
        try {
            Response response = authService.validateUser(request);
            return ResponseEntity.ok(response);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @PostMapping("/setUsername")
    @CrossOrigin
    public ResponseEntity<Response> setUsername(@RequestBody SetUsernameRequest request) {
        try {
            Response response = authService.setUsername(request);
            return ResponseEntity.ok(response);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }

    @PostMapping("/setSpin")
    @CrossOrigin
    public ResponseEntity<Response> setSpin(@RequestBody SetSecretPinRequest request) {
        try {
            Response response = authService.setSpin(request);
            return ResponseEntity.ok(response);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(false, e.getMessage(), null, null),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }
    }
}
