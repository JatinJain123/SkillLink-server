package com.jatinjain.SkillLink.models.userRequests.auth;

public class SetUsernameRequest {
    private String userId;
    private String email;
    private String userName;

    public SetUsernameRequest(String userId, String email, String userName) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
