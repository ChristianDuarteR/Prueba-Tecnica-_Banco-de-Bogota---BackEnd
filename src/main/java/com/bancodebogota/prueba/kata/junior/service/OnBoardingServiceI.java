package com.bancodebogota.prueba.kata.junior.service;

import org.springframework.stereotype.Service;

import com.bancodebogota.prueba.kata.junior.dto.OnboardingModelDto;

@Service
public interface OnBoardingServiceI {
    public void createOnboardingForContributor(String contributorId, OnboardingModelDto onboardingDto);

    public void updateFields(String contributorId, OnboardingModelDto onboardingDto);
}
