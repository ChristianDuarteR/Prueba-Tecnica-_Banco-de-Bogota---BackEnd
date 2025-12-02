package com.bancodebogota.prueba.kata.junior.exception;

public class ContributorBadRequestException extends RuntimeException {
    
    public ContributorBadRequestException(String message) {
        super("Campo: " + message + " es invalido o esta incompleto.");
    }
}
