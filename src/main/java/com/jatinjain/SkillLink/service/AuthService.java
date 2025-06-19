package com.jatinjain.SkillLink.service;

import com.jatinjain.SkillLink.model.DTOs.auth.AuthResponse;
import com.jatinjain.SkillLink.model.DTOs.auth.LoginRequest;
import com.jatinjain.SkillLink.model.DTOs.auth.SetSecretPinRequest;
import com.jatinjain.SkillLink.model.DTOs.auth.SetUsernameRequest;

public interface AuthService {
    AuthResponse signup(LoginRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse setUsername(SetUsernameRequest request);
    AuthResponse setSecretPin(SetSecretPinRequest request);
}