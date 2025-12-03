package com.bancodebogota.prueba.kata.junior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodebogota.prueba.kata.junior.dto.OnBoardingDto;
import com.bancodebogota.prueba.kata.junior.exception.OnboardingNotFoundException;
import com.bancodebogota.prueba.kata.junior.service.imp.OnBoardingService;

@RestController
@RequestMapping("/api/v1/onboardings")
public class OnBoardingController {

    private OnBoardingService onBoardingService;

    public OnBoardingController(@Autowired OnBoardingService onBoardingService) {
        this.onBoardingService = onBoardingService;
    }

    @GetMapping
    public ResponseEntity<Iterable<OnBoardingDto>> getOnboardings() {
        try {
            Iterable<OnBoardingDto> onboardingsDtos = onBoardingService.getAllOnBoardings();
            return ResponseEntity.status(HttpStatus.OK).body(onboardingsDtos);

        } catch (OnboardingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("X-Error-Message", e.getMessage())
                .build();
        }
    }
    
}
