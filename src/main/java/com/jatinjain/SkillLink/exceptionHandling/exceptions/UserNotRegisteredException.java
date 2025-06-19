package com.jatinjain.SkillLink.exceptionHandling.exceptions;

public class UserNotRegisteredException extends RuntimeException{
    public UserNotRegisteredException(String message) {
        super(message);
    }
}
