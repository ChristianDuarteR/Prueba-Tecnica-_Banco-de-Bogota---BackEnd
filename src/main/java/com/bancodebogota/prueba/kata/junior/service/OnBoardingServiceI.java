package com.bancodebogota.prueba.kata.junior.service;

import org.springframework.stereotype.Service;

import com.bancodebogota.prueba.kata.junior.dto.OnboardingModelDto;
import com.bancodebogota.prueba.kata.junior.model.ContributorModel;

@Service
public interface OnBoardingServiceI {
    public void createOnboardingForContributor(ContributorModel contributorModel, OnboardingModelDto onboardingDto);

    public void updateFields(String contributorId, OnboardingModelDto onboardingDto);
}
