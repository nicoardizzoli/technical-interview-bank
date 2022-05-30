package com.nicoardizzoli.technicalinterviewbank.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
