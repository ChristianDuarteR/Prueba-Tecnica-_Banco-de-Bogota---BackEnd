package com.bancodebogota.prueba.kata.junior.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ContributorDto {

    @NotBlank(message = "First name cannot be null or empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or empty")
    private String lastName;

    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email format is invalid")
    private String email;

    @NotNull(message = "Join date cannot be null")
    @FutureOrPresent(message = "Join date must be today or a future date")
    private LocalDate joinDate;

    private List<OnBoardingDto> onboardings;


    public static ContributorDto convertToDto(ContributorModel model) {
        ContributorDto dto = new ContributorDto();
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setJoinDate(model.getJoinDate());

        dto.setOnboardings(
            Optional.ofNullable(model.getOnboardings())
                .orElse(Collections.emptyList())
                .stream()
                .map(OnBoardingDto::convertToDto)
                .toList()
        );

        return dto;
    }


    public static ContributorModel convertToEntity(ContributorDto dto) {

        ContributorModel model = new ContributorModel();
        model.validateNotNull(dto);
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setEmail(dto.getEmail());
        model.setJoinDate(dto.getJoinDate());

        if (dto.getOnboardings() != null) {
            List<OnboardingModel> list = dto.getOnboardings().stream()
                .map(o -> OnBoardingDto.convertToEntity(o, model))
                .toList();
            model.setOnboardings(list);
        }

        return model;
    }
}
