package com.bancodebogota.prueba.kata.junior.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;
import com.bancodebogota.prueba.kata.junior.dto.OnboardingModelDto;
import com.bancodebogota.prueba.kata.junior.exception.ContributorBadRequestException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="contributors")
public class ContributorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String contributorId;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @Column
    public String email;

    @Column
    public Date JoinDate;

    @Column
    public Date onBoardingTechnicalDateAssigned;

    @OneToMany(mappedBy = "contributor", cascade = CascadeType.ALL)
    private List<OnboardingModel> onboardings = new ArrayList<>();


    public void updateFields(ContributorDto contributorDto) {
        validateNotNull(contributorDto);
        this.firstName = contributorDto.getFirstName();
        this.lastName = contributorDto.getLastName();
        this.email = contributorDto.getEmail();
        this.JoinDate = contributorDto.getJoinDate();
        if (contributorDto.getOnboardings() != null) {
            this.onboardings = contributorDto.getOnboardings().stream()
                .map(dto -> OnboardingModelDto.convertToEntity(dto, this))
                .toList();
        } else {
            this.onboardings = null;
        }
    }

    public void validateNotNull(ContributorDto contributorDto) {
        if (contributorDto.getEmail() == null) {
            throw new ContributorBadRequestException("Email cannot be null");
        } if (contributorDto.getFirstName() == null) {
            throw new ContributorBadRequestException("First name cannot be null");
        } if (contributorDto.getLastName() == null) {
            throw new ContributorBadRequestException("Last name cannot be null");
        } if (contributorDto.getJoinDate() == null) {
            throw new ContributorBadRequestException("Join date cannot be null");
        } 
        
    }
}
