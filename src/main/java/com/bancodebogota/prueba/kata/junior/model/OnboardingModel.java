package com.bancodebogota.prueba.kata.junior.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.sql.Date;
import com.bancodebogota.prueba.kata.junior.dto.OnboardingModelDto;
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
    private Boolean onBoardingStatus;

    @ManyToOne
    @JoinColumn(name = "contributor_id")
    private ContributorModel contributor;

    @Column
    private Date onBoardingTechnicalDateAssignedate;

    public void updateFields(OnboardingModelDto onboardingModelDto) {
        this.type = onboardingModelDto.getType();
        this.onBoardingStatus = onboardingModelDto.getOnBoardingStatus();
        this.onBoardingTechnicalDateAssignedate = onboardingModelDto.getOnBoardingTechnicalDateAssigned();

    }
}
