package com.bancodebogota.prueba.kata.junior.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@Entity(name="contributors")
public class ContributorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @Column
    public String email;

    @Column
    public Date JoinDate;

    @Column
    public boolean statusOnBoarding;

    @Column
    public boolean statusTechnicalOnBoarding;

    @Column
    public Date onBoardingTechnicalDateAssigned;
}
