package com.jatinjain.SkillLink.models.userReponses;

public class AuthResponse {
    private String message;

    public AuthResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
