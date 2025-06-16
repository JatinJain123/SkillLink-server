package com.jatinjain.SkillLink.services;

import com.jatinjain.SkillLink.exceptions.UserNotRegisteredException;
import com.jatinjain.SkillLink.exceptions.WrongCredentialsException;
import com.jatinjain.SkillLink.models.mainModels.User;
import com.jatinjain.SkillLink.models.userReponses.AuthResponse;
import com.jatinjain.SkillLink.models.userRequests.auth.SetSecretPinRequest;
import com.jatinjain.SkillLink.models.userRequests.auth.SetUsernameRequest;
import com.jatinjain.SkillLink.repositories.UserRepository;
import com.jatinjain.SkillLink.models.userRequests.auth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{
    final private UserRepository userRepo;
    final private PasswordEncoder passwordEncoder;
    static final private String EMAIL_REGEX = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.\\p{L}{2,})$";

    @Autowired
    public AuthServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse addUser(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if(email.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("email and password cannot be empty");
        }

        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is not valid");
        }

        if(userRepo.findByEmail(email).isPresent()){
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

        userRepo.save(user);
        return new AuthResponse(true,"user successfully registered", user.getId(), user.getEmail());
    }

    public AuthResponse validateUser(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if(email.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("email and password cannot be empty");
        }

        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is not valid");
        }

        return userRepo.findByEmail(email)
                .map(user -> {
                    if(passwordEncoder.matches(password, user.getPassword())) {
                        return new AuthResponse(true, "Welcome Back !!", user.getId(), user.getEmail());
                    } else {
                        throw new WrongCredentialsException("Bad Credentials");
                    }
                })
                .orElseThrow(() -> new UserNotRegisteredException("user is not registered"));
    }

    public AuthResponse setUsername(SetUsernameRequest request) {
        String userId = request.getUserId();
        String email = request.getEmail();
        String username = request.getUsername();

        if(email.isBlank() || userId.isBlank() || username.isBlank()) {
            throw new IllegalArgumentException("credentials cannot be empty");
        }

        if(!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email is not valid");
        }

        return userRepo.findByEmail(email)
                .map(user -> {
                    if(userId.equals(user.getId())) {
                        user.setUsername(username);
                        userRepo.save(user);
                        return new AuthResponse(true, "username added successfully", null, null);
                    } else {
                        throw new WrongCredentialsException("Bad Credentials");
                    }
                })
                .orElseThrow(() -> new UserNotRegisteredException("user is not registered"));
    }

    public AuthResponse setSecretPin(SetSecretPinRequest request) {
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

        return userRepo.findByEmail(email)
                .map(user -> {
                    if(userId.equals(user.getId())) {
                        user.setSecretPin(secretPin);
                        userRepo.save(user);
                        return new AuthResponse(true, "secret pin added successfully", null, null);
                    } else {
                        throw new WrongCredentialsException("Bad Credentials");
                    }
                })
                .orElseThrow(() -> new UserNotRegisteredException("user is not registered"));
    }
}
