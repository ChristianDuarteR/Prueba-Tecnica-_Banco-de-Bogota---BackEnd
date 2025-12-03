package com.bancodebogota.prueba.kata.junior.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;

import lombok.Data;

@Data
public class ContributorDto {
    public String firstName;
    public String lastName;
    public String email;
    public LocalDate  joinDate;
    public List<OnBoardingDto> onboardings;


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
