package com.bancodebogota.prueba.kata.junior.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bancodebogota.prueba.kata.junior.dto.OnBoardingDto;
import com.bancodebogota.prueba.kata.junior.model.ContributorModel;

@Service
public interface OnBoardingServiceI {
    public void createOnboardingForContributor(ContributorModel contributorModel, OnBoardingDto onboardingDto);

    public void updateFields(String contributorId, OnBoardingDto onboardingDto);

    public List<OnBoardingDto> getAllOnBoardings();
}
