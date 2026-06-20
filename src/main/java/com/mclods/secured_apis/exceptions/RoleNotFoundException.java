package com.mclods.secured_apis.exceptions;

public class RoleNotFoundException extends Exception {
    String message;

    public RoleNotFoundException(Integer roleId) {
        message = "Role with id: %d not found".formatted(roleId);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
