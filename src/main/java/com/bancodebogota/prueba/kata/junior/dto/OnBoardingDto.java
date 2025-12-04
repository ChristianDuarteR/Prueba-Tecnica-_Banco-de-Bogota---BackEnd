package com.bancodebogota.prueba.kata.junior.dto;

import java.time.LocalDate;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;
import com.bancodebogota.prueba.kata.junior.type.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;

import lombok.Data;

@Data
public class OnBoardingDto {

    @JsonIgnore
    private String id;

    @NotNull(message = "Type cannot be null")
    private Type type;

    @NotNull(message = "Onboarding status cannot be null")
    private Boolean onBoardingStatus;

    private String title;

    @NotNull(message = "Technical onboarding date cannot be null")
    @FutureOrPresent(message = "Technical onboarding date must be today or a future date")
    private LocalDate onBoardingTechnicalDateAssigned;

    @JsonIgnore
    private String contributorId;

    public static OnBoardingDto convertToDto(OnboardingModel model) {
        OnBoardingDto dto = new OnBoardingDto();
        dto.setType(model.getType());
        dto.setOnBoardingStatus(model.getOnBoardingStatus());
        dto.setOnBoardingTechnicalDateAssigned(
            model.getOnBoardingTechnicalDateAssignedate()
        );
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());

        if (model.getContributor() != null) {
            dto.setContributorId(model.getContributor().getContributorId());
        }
        return dto;
    }

    public static OnboardingModel convertToEntity(
            OnBoardingDto dto,
            ContributorModel contributor
    ) {
        OnboardingModel model = new OnboardingModel();
        model.setType(dto.getType());
        model.setOnBoardingStatus(dto.getOnBoardingStatus());
        model.setOnBoardingTechnicalDateAssignedate(
            dto.getOnBoardingTechnicalDateAssigned()
        );
        model.setContributor(contributor);
        model.setTitle(dto.getTitle());
        return model;
    }
}
