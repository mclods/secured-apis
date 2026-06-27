package com.mclods.secured_apis.exceptions;

public class TacoNotFoundException extends AppException {
    public TacoNotFoundException(Integer tacoId) {
        super("Taco with id: %d not found".formatted(tacoId));
    }
}
