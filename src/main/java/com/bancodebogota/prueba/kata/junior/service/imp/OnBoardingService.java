package com.bancodebogota.prueba.kata.junior.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bancodebogota.prueba.kata.junior.dto.OnboardingModelDto;
import com.bancodebogota.prueba.kata.junior.exception.OnBoardingBowFoundException;
import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;
import com.bancodebogota.prueba.kata.junior.repository.ContributorRepository;
import com.bancodebogota.prueba.kata.junior.repository.OnBoardingRepository;
import com.bancodebogota.prueba.kata.junior.service.OnBoardingServiceI;

@Service
public class OnBoardingService implements OnBoardingServiceI {

    private final OnBoardingRepository onboardingRepository;
    private final ContributorRepository contributorRepository;

    public OnBoardingService(@Autowired OnBoardingRepository onboardingRepository,
                             @Autowired ContributorRepository contributorRepository) {
        this.onboardingRepository = onboardingRepository;
        this.contributorRepository = contributorRepository;
    }

    @Override
    public void createOnboardingForContributor(ContributorModel contributor, OnboardingModelDto onboardingDto) {
        OnboardingModel onboarding = OnboardingModelDto.convertToEntity(onboardingDto, contributor);
        contributor.getOnboardings().add(onboarding);
        contributorRepository.save(contributor);
        onboardingRepository.save(onboarding);
    }

    @Override
    public void updateFields(String contributorId, OnboardingModelDto onboardingDto) {
        OnboardingModel onboarding;
        if (onboardingDto.getId() != null) {
            onboarding = onboardingRepository.findById(onboardingDto.getContributorId())
                    .orElseThrow(() -> new OnBoardingBowFoundException(contributorId));
        } else {
            onboarding = new OnboardingModel();
        }
        onboarding.updateFields(onboardingDto);
        onboardingRepository.save(onboarding);
    }
}
