package com.bancodebogota.prueba.kata.junior.exception;

public class OnBoardingBowFoundException extends RuntimeException {
    public OnBoardingBowFoundException(String message) {
        super("No existe el onboarding para el contributor con id: " + message);
    }
}
