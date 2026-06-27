package com.mclods.secured_apis.exceptions;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(Integer userId) {
        super("User with id: %d not found".formatted(userId));
    }
}
