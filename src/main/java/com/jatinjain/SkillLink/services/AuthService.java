package com.jatinjain.SkillLink.services;

import com.jatinjain.SkillLink.models.User;
import com.jatinjain.SkillLink.models.userReponses.AuthResponse;
import com.jatinjain.SkillLink.repository.AuthRepository;
import com.jatinjain.SkillLink.models.userRequests.AuthRequest;
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

    public AuthResponse addUser(AuthRequest request) throws Exception {
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
        user.setUserName("");
        user.setRealName("");
        user.setSpin(-1);
        user.setBio("");

        try {
            authRepo.save(user);
            return new AuthResponse(true,"user successfully registered", user.getId(), user.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }

    public AuthResponse validateUser(AuthRequest request) throws Exception {
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
                    .orElse(new AuthResponse(false,"user not registered", null, null));
        } catch (Exception e) {
            throw new RuntimeException("database error: "+ e.getMessage());
        }
    }
}
