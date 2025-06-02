package com.jatinjain.SkillLink.services;

import com.jatinjain.SkillLink.models.mainModels.User;
import com.jatinjain.SkillLink.models.userReponses.AuthResponse;
import com.jatinjain.SkillLink.models.userRequests.auth.SetSecretPinRequest;
import com.jatinjain.SkillLink.models.userRequests.auth.SetUsernameRequest;
import com.jatinjain.SkillLink.repository.AuthRepository;
import com.jatinjain.SkillLink.models.userRequests.auth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    final private AuthRepository authRepo;
    final private PasswordEncoder passwordEncoder;
    static final private String EMAIL_REGEX = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";

    @Autowired
    public AuthService(AuthRepository authRepo, PasswordEncoder passwordEncoder) {
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse addUser(LoginRequest request) throws Exception {
        String email = request.getEmail();
        String password = request.getPassword();

        if(email.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("email and password cannot be empty");
        }

        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is not valid");
        }

        if(authRepo.findByEmail(email).isPresent()){
            throw new DuplicateKeyException("email is already registered");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername("");
        user.setRealName("");
        user.setSecretPin(-1);
        user.setBio("");

        try {
            authRepo.save(user);
            return new AuthResponse(true,"user successfully registered", user.getId(), user.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }

    public AuthResponse validateUser(LoginRequest request) throws Exception {
        String email = request.getEmail();
        String password = request.getPassword();

        if(email.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("email and password cannot be empty");
        }

        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is not valid");
        }

        try {
            return authRepo.findByEmail(email)
                    .map(user -> {
                        if(passwordEncoder.matches(password, user.getPassword())) {
                            return new AuthResponse(true, "Welcome Back !!", user.getId(), user.getEmail());
                        } else {
                            return new AuthResponse(false, "wrong credentials", null, null);
                        }
                    })
                    .orElse(new AuthResponse(false,"user is not registered", null, null));
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }

    public AuthResponse setUsername(SetUsernameRequest request) throws Exception {
        String userId = request.getUserId();
        String email = request.getEmail();
        String username = request.getUsername();

        if(email.isBlank() || userId.isBlank() || username.isBlank()) {
            throw new IllegalArgumentException("credentials cannot be empty");
        }

        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is not valid");
        }

        try {
            return authRepo.findByEmail(email)
                    .map(user -> {
                        if(userId.equals(user.getId())) {
                            user.setUsername(username);
                            authRepo.save(user);
                            return new AuthResponse(true, "username added successfully", null, null);
                        } else {
                            return new AuthResponse(false, "credentials did not match", null, null);
                        }
                    })
                    .orElse(new AuthResponse(false, "user is not registered", null, null));
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }

    public AuthResponse setSecretPin(SetSecretPinRequest request) throws Exception {
        String userId = request.getUserId();
        String email = request.getEmail();
        int secretPin = request.getSecretPin();

        if(email.isBlank() || userId.isBlank()) {
            throw new IllegalArgumentException("credentials cannot be empty");
        }

        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is not valid");
        }

        if(secretPin <= 999 || secretPin >= 10000) {
            throw new IllegalArgumentException("secret pin is not valid");
        }

        try {
            return authRepo.findByEmail(email)
                    .map(user -> {
                        if(userId.equals(user.getId())) {
                            user.setSecretPin(secretPin);
                            authRepo.save(user);
                            return new AuthResponse(true, "secret pin added successfully", null, null);
                        } else {
                            return new AuthResponse(false, "credentials did not match", null, null);
                        }
                    })
                    .orElse(new AuthResponse(false, "user is not registered", null, null));
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }
}
