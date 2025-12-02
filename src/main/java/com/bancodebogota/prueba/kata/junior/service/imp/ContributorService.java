package com.bancodebogota.prueba.kata.junior.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;
import com.bancodebogota.prueba.kata.junior.dto.OnboardingModelDto;
import com.bancodebogota.prueba.kata.junior.exception.ContributorAlreadyExistsException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorBadRequestException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorNotFoundException;
import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.repository.ContributorRepository;
import com.bancodebogota.prueba.kata.junior.service.ContributorServiceI;
import com.bancodebogota.prueba.kata.junior.service.OnBoardingServiceI;

@Service
public class ContributorService implements ContributorServiceI {

    private final ContributorRepository contributorRepository;

    private final OnBoardingServiceI onboardingService;

    
    public ContributorService(@Autowired ContributorRepository contributorRepository,
                             @Autowired OnBoardingServiceI onboardingService) {
        this.contributorRepository = contributorRepository;
        this.onboardingService = onboardingService;
    }

    @Override
    public Iterable<ContributorDto> getAllContributors() {
        List<ContributorModel> contributors = contributorRepository.findAll();
        if (contributors.isEmpty()) {
            throw new ContributorNotFoundException("No contributors found");
        }
        return contributors.stream()
            .map(ContributorDto::convertToDto)
            .toList();
    }
    
    @Override
    public ContributorDto getContributor(String email) {
        ContributorModel contributor = contributorRepository.findByEmail(email)
            .orElseThrow(() -> new ContributorNotFoundException(email));
        return ContributorDto.convertToDto(contributor);
    }

    public ContributorDto createContributor(ContributorDto contributorDto) throws ContributorBadRequestException {
        if (contributorRepository.findByEmail(contributorDto.getEmail()).isPresent()) {
            throw new ContributorAlreadyExistsException(contributorDto.getEmail());
        }
        ContributorModel contributor = ContributorDto.convertToEntity(contributorDto);
        ContributorModel contributorModel = contributorRepository.save(contributor);
        if (contributorDto.getOnboardings() != null) {
            for (OnboardingModelDto onboardingDto : contributorDto.getOnboardings()) {
                onboardingService.createOnboardingForContributor(contributorModel, onboardingDto);
            }
        }
        ContributorModel savedContributor = contributorRepository.findById(contributor.getContributorId())
                .orElseThrow(() -> new ContributorBadRequestException("Error al cargar el contributor recién creado"));
        return ContributorDto.convertToDto(savedContributor);
    }


    @Override
    public ContributorDto updateContributor(String email, ContributorDto contributorDto) {
        ContributorModel existingContributor = contributorRepository.findByEmail(email)
            .orElseThrow(() -> new ContributorNotFoundException(email));

        existingContributor.updateFields(contributorDto);

        if (contributorDto.getOnboardings() != null) {
            contributorDto.getOnboardings().forEach(onboardingDto -> {
                onboardingService.updateFields(existingContributor.getContributorId(), onboardingDto);
            });
        }

        contributorRepository.save(existingContributor);
        ContributorModel updatedContributor = contributorRepository.findById(existingContributor.getContributorId())
            .orElseThrow(() -> new ContributorNotFoundException(email));

        return ContributorDto.convertToDto(updatedContributor);
    }



    @Override
    public void deleteContributor(String email) {
        ContributorModel existingContributor = contributorRepository.findByEmail(email)
            .orElseThrow(() -> new ContributorNotFoundException(email));
        contributorRepository.delete(existingContributor);
    }
}
