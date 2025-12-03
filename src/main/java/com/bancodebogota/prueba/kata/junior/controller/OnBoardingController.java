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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/onboardings")
@Tag(name = "Onboardings", description = "Gestión de procesos de onboarding")
public class OnBoardingController {

    private OnBoardingService onBoardingService;

    public OnBoardingController(@Autowired OnBoardingService onBoardingService) {
        this.onBoardingService = onBoardingService;
    }

    @Operation(
        summary = "Obtener todos los onboardings",
        description = "Retorna el listado completo de onboardings registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de onboardings obtenida correctamente",
            content = @Content(schema = @Schema(implementation = OnBoardingDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron onboardings"
        )
    })
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
