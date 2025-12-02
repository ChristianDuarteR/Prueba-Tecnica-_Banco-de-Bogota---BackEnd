package com.bancodebogota.prueba.kata.junior.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ContributorDto {
    public String firstName;
    public String lastName;
    public String email;
    public Date JoinDatel;
    public boolean statusOnBoarding;
    public boolean statusTechnicalOnBoarding;
    public Date onBoardingTechnicalDateAssigned;
}
