package com.bancodebogota.prueba.kata.junior.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;
import com.bancodebogota.prueba.kata.junior.dto.OnBoardingDto;
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
    public LocalDate JoinDate;

    @Column
    public LocalDate onBoardingTechnicalDateAssigned;

    @OneToMany(mappedBy = "contributor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OnboardingModel> onboardings = new ArrayList<>();


public void updateFields(ContributorDto contributorDto) {
    validateNotNull(contributorDto);

    this.firstName = contributorDto.getFirstName();
    this.lastName = contributorDto.getLastName();
    this.email = contributorDto.getEmail();
    this.JoinDate = contributorDto.getJoinDate();

    if (contributorDto.getOnboardings() != null) {
        this.onboardings.clear();
        for (OnBoardingDto dto : contributorDto.getOnboardings()) {
            OnboardingModel entity = OnBoardingDto.convertToEntity(dto, this);
            this.onboardings.add(entity);
        }

    } else {
        this.onboardings.clear();
    }
}

public void validateNotNull(ContributorDto contributorDto) {

    if (contributorDto == null) {
        throw new ContributorBadRequestException("Contributor cannot be null");
    }
    if (contributorDto.getEmail() == null || contributorDto.getEmail().isBlank()) {
        throw new ContributorBadRequestException("Email cannot be null or empty");
    }
    if (!isValidEmail(contributorDto.getEmail())) {
        throw new ContributorBadRequestException("Email format is invalid");
    }
    if (contributorDto.getFirstName() == null || contributorDto.getFirstName().isBlank()) {
        throw new ContributorBadRequestException("First name cannot be null or empty");
    }
    if (contributorDto.getLastName() == null || contributorDto.getLastName().isBlank()) {
        throw new ContributorBadRequestException("Last name cannot be null or empty");
    }
    if (contributorDto.getJoinDate() == null) {
        throw new ContributorBadRequestException("Join date cannot be null");
    }
    if (contributorDto.getJoinDate().isBefore(LocalDate.now())) {
        throw new ContributorBadRequestException("Join date must be today or a future date");
    }
}

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }
}