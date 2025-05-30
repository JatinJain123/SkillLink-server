package com.jatinjain.SkillLink.models.userRequests.auth;

public class SetSecretPinRequest {
    private String userId;
    private String email;
    private int secretPin;

    public SetSecretPinRequest(String email, int secretPin, String userId) {
        this.email = email;
        this.secretPin = secretPin;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSecretPin() {
        return secretPin;
    }

    public void setSecretPin(int secretPin) {
        this.secretPin = secretPin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
