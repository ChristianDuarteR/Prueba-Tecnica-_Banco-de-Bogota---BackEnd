package com.bancodebogota.prueba.kata.junior.exception;

public class ContributorAlreadyExistsException extends RuntimeException {
    public ContributorAlreadyExistsException(String message) {
        super("Contributor already exists with email: " + message);
    }
}
