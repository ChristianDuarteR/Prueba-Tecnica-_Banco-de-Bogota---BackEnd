package com.bancodebogota.prueba.kata.junior.dto;

import java.sql.Date;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;
import com.bancodebogota.prueba.kata.junior.type.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OnboardingModelDto {
    
    @JsonIgnore
    private String id;
    
    @Column
    private Type type;          
            
    @Column
    private Boolean onBoardingStatus; 
    
    @Column
    private Date onBoardingTechnicalDateAssigned;           
    
    @Column
    @JsonIgnore
    private String contributorId;
    
    public static OnboardingModelDto convertToDto(OnboardingModel model) {
        OnboardingModelDto dto = new OnboardingModelDto();
        dto.setType(model.getType());
        dto.setOnBoardingStatus(model.getOnBoardingStatus());
        dto.setOnBoardingTechnicalDateAssigned(model.getOnBoardingTechnicalDateAssignedate());
        dto.setId(model.getId());
        if (model.getContributor() != null) {
            dto.setContributorId(model.getContributor().getContributorId());
        }
        return dto;
    }

    public static OnboardingModel convertToEntity(OnboardingModelDto dto, ContributorModel contributor) {
        OnboardingModel model = new OnboardingModel();
        model.setType(dto.getType());
        model.setOnBoardingStatus(dto.getOnBoardingStatus());
        model.setOnBoardingTechnicalDateAssignedate(dto.getOnBoardingTechnicalDateAssigned());
        model.setContributor(contributor);
        return model;
    }
}
