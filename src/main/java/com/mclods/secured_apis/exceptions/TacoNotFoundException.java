package com.mclods.secured_apis.exceptions;

public class TacoNotFoundException extends Exception {
    String message;

    public TacoNotFoundException(Integer tacoId) {
        message = "Taco with id: %d not found".formatted(tacoId);
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
