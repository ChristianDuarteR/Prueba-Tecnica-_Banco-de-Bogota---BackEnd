package com.bancodebogota.prueba.kata.junior.service;

import org.springframework.stereotype.Service;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;

@Service
public interface EmailServiceI {

    void sendOnboardingReminder(ContributorModel contributor, OnboardingModel onboarding, int daysRemaining);
    
}
