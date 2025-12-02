package com.bancodebogota.prueba.kata.junior.dto;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import lombok.Data;

@Data
public class ContributorDto {
    public String firstName;
    public String lastName;
    public String email;
    public Date joinDate;
    public List<OnboardingModelDto> onboardings;


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
                    .map(OnboardingModelDto::convertToDto)
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
        return model;
    }


}
