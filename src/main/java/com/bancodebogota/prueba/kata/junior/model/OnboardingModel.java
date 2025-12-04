package com.bancodebogota.prueba.kata.junior.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.bancodebogota.prueba.kata.junior.dto.OnBoardingDto;
import com.bancodebogota.prueba.kata.junior.type.Type;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "onboardings")
public class OnboardingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

    @Column
    private String title;

    @Column
    private Boolean onBoardingStatus;

    @ManyToOne
    @JoinColumn(name = "contributor_id")
    private ContributorModel contributor;

    @Column
    private LocalDate onBoardingTechnicalDateAssignedate;

    public void updateFields(OnBoardingDto onboardingModelDto) {
        this.type = onboardingModelDto.getType();
        this.onBoardingStatus = onboardingModelDto.getOnBoardingStatus();
        this.onBoardingTechnicalDateAssignedate = onboardingModelDto.getOnBoardingTechnicalDateAssigned();

    }
}
