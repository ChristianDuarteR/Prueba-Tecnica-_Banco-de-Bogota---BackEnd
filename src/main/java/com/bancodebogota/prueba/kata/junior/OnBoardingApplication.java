package com.bancodebogota.prueba.kata.junior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition
@SpringBootApplication
@EnableScheduling
public class OnBoardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnBoardingApplication.class, args);
	}

}
