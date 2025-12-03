package com.bancodebogota.prueba.kata.junior.exception;

public class OnboardingNotFoundException extends RuntimeException{
    public OnboardingNotFoundException(String message) {
        super("No se encontraron onboardings");
    }
    
}
