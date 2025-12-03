package com.bancodebogota.prueba.kata.junior.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bancodebogota.prueba.kata.junior.dto.OnBoardingDto;
import com.bancodebogota.prueba.kata.junior.exception.OnBoardingBowFoundException;
import com.bancodebogota.prueba.kata.junior.exception.OnboardingNotFoundException;
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
    public void createOnboardingForContributor(ContributorModel contributor, OnBoardingDto onboardingDto) {
        OnboardingModel onboarding = OnBoardingDto.convertToEntity(onboardingDto, contributor);
        contributor.getOnboardings().add(onboarding);
        contributorRepository.save(contributor);
        onboardingRepository.save(onboarding);
    }

    @Override
    public void updateFields(String contributorId, OnBoardingDto onboardingDto) {
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

    @Override
    public List<OnBoardingDto> getAllOnBoardings() {
        List<OnboardingModel> onboardingModels = onboardingRepository.findAll();
        if (onboardingModels.isEmpty()) throw new OnboardingNotFoundException("");

        return onboardingModels.stream()
            .map(OnBoardingDto::convertToDto)
            .toList();
    }
}
