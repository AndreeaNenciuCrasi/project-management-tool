package com.example.projecttool.exceptions.UserNotFoundException;

public class UserNotFoundExceptionResponse {
    private String userNotFound;

    public UserNotFoundExceptionResponse(String userNotFound) {
        this.userNotFound = userNotFound;
    }

    public String getUserNotFound() {
        return userNotFound;
    }

    public void setUserNotFound(String userNotFound) {
        this.userNotFound = userNotFound;
    }
}
