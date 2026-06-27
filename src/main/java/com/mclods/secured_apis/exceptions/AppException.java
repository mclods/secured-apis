package com.mclods.secured_apis.exceptions;

public class AppException extends Exception {
    String message;

    public AppException(String message) {
        this.message = "AppException: %s".formatted(message);
    }

    public AppException() {
        this.message = "AppException: Something went wrong!";
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
