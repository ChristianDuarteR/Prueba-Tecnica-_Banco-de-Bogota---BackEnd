package com.bancodebogota.prueba.kata.junior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;

public interface OnBoardingRepository extends JpaRepository<OnboardingModel, String> {

}