package com.githubapi.infrastructure.controller.error;

public class WrongAcceptHeaderException extends RuntimeException {
    public WrongAcceptHeaderException(String message) {
        super(message);
    }
}
