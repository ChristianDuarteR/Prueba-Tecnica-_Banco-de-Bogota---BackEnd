package com.bancodebogota.prueba.kata.junior.exception;

public class ContributorNotFoundException extends RuntimeException {
    public ContributorNotFoundException(String Id) {
        super("Contributor not found with ID: " + Id);
    }
    
}
