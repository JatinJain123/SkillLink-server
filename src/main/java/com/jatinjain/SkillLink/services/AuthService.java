package com.jatinjain.SkillLink.services;

import com.jatinjain.SkillLink.models.userReponses.AuthResponse;
import com.jatinjain.SkillLink.models.userRequests.auth.LoginRequest;
import com.jatinjain.SkillLink.models.userRequests.auth.SetSecretPinRequest;
import com.jatinjain.SkillLink.models.userRequests.auth.SetUsernameRequest;

public interface AuthService {
    AuthResponse addUser(LoginRequest request);
    AuthResponse validateUser(LoginRequest request);
    AuthResponse setUsername(SetUsernameRequest request);
    AuthResponse setSecretPin(SetSecretPinRequest request);
}