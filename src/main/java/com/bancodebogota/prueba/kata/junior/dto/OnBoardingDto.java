package com.bancodebogota.prueba.kata.junior.dto;

import java.sql.Date;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;
import com.bancodebogota.prueba.kata.junior.type.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class OnBoardingDto {
    
    @JsonIgnore
    private String id;
    
    @Column
    private Type type;          
            
    @Column
    private Boolean onBoardingStatus; 

    @Column
    private String title;
    
    @Column
    private Date onBoardingTechnicalDateAssigned;           
    
    @Column
    @JsonIgnore
    private String contributorId;
    
    public static OnBoardingDto convertToDto(OnboardingModel model) {
        OnBoardingDto dto = new OnBoardingDto();
        dto.setType(model.getType());
        dto.setOnBoardingStatus(model.getOnBoardingStatus());
        dto.setOnBoardingTechnicalDateAssigned(model.getOnBoardingTechnicalDateAssignedate());
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        if (model.getContributor() != null) {
            dto.setContributorId(model.getContributor().getContributorId());
        }
        return dto;
    }

    public static OnboardingModel convertToEntity(OnBoardingDto dto, ContributorModel contributor) {
        OnboardingModel model = new OnboardingModel();
        model.setType(dto.getType());
        model.setOnBoardingStatus(dto.getOnBoardingStatus());
        model.setOnBoardingTechnicalDateAssignedate(dto.getOnBoardingTechnicalDateAssigned());
        model.setContributor(contributor);
        model.setTitle(dto.getTitle());
        return model;
    }
}
