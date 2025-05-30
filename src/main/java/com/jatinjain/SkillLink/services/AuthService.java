package com.jatinjain.SkillLink.services;

import com.jatinjain.SkillLink.models.mainModels.User;
import com.jatinjain.SkillLink.models.userReponses.Response;
import com.jatinjain.SkillLink.models.userRequests.auth.SetSecretPinRequest;
import com.jatinjain.SkillLink.models.userRequests.auth.SetUsernameRequest;
import com.jatinjain.SkillLink.repository.AuthRepository;
import com.jatinjain.SkillLink.models.userRequests.auth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
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

    public Response addUser(LoginRequest request) throws Exception {
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
        user.setSpin(-1);
        user.setBio("");

        try {
            authRepo.save(user);
            return new Response(true,"user successfully registered", user.getId(), user.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }

    public Response validateUser(LoginRequest request) throws Exception {
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
                            return new Response(true, "Welcome Back !!", user.getId(), user.getEmail());
                        } else {
                            return new Response(false, "wrong credentials", null, null);
                        }
                    })
                    .orElse(new Response(false,"user is not registered", null, null));
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }

    public Response setUsername(SetUsernameRequest request) throws Exception {
        String userId = request.getUserId();
        String email = request.getEmail();
        String username = request.getUserName();

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
                            return new Response(true, "username added successfully", null, null);
                        } else {
                            return new Response(false, "credentials did not match", null, null);
                        }
                    })
                    .orElse(new Response(false, "user is not registered", null, null));
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }

    public Response setSpin(SetSecretPinRequest request) throws Exception {
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
                            user.setSpin(secretPin);
                            authRepo.save(user);
                            return new Response(true, "secret pin added successfully", null, null);
                        } else {
                            return new Response(false, "credentials did not match", null, null);
                        }
                    })
                    .orElse(new Response(false, "user is not registered", null, null));
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }
}
