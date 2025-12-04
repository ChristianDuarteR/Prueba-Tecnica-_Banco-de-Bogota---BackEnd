package com.bancodebogota.prueba.kata.junior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;
import com.bancodebogota.prueba.kata.junior.exception.ContributorAlreadyExistsException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorBadRequestException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorNotFoundException;
import com.bancodebogota.prueba.kata.junior.service.imp.ContributorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/contributors")
@Tag(name = "Contributors", description = "Gestión de colaboradores del sistema")
public class ContributorController {

    private final ContributorService contributorService;

    public ContributorController(@Autowired ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @Operation(
        summary = "Obtener todos los colaboradores",
        description = "Retorna el listado completo de colaboradores registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colaboradores obtenida correctamente",
            content = @Content(schema = @Schema(implementation = ContributorDto.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron colaboradores")
    })
    @GetMapping
    public ResponseEntity<Iterable<ContributorDto>> getContributors() throws ContributorNotFoundException {
        try{
            Iterable<ContributorDto> contributors = contributorService.getAllContributors();
            return ResponseEntity.ok(contributors);
        } catch (ContributorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Obtener colaborador por email",
        description = "Retorna la información de un colaborador según su correo electrónico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador encontrado",
            content = @Content(schema = @Schema(implementation = ContributorDto.class))),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    @GetMapping("/{email}")
    public ResponseEntity<ContributorDto> getContributor(@PathVariable String email) throws ContributorNotFoundException {
        try{
            ContributorDto contributor = contributorService.getContributor(email);
            return ResponseEntity.status(HttpStatus.OK).body(contributor);
        } catch (ContributorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }

    @Operation(
        summary = "Crear colaborador",
        description = "Crea un nuevo colaborador en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Colaborador creado correctamente",
            content = @Content(schema = @Schema(implementation = ContributorDto.class))),
        @ApiResponse(responseCode = "400", description = "El colaborador ya existe o datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ContributorDto> createContributor(@Valid @RequestBody ContributorDto contributorDto) throws ContributorBadRequestException{
        try {
            ContributorDto createdContributor = contributorService.createContributor(contributorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContributor);
        } catch (ContributorAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }
    
    @Operation(
        summary = "Actualizar colaborador",
        description = "Actualiza la información de un colaborador existente por email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Colaborador actualizado correctamente",
            content = @Content(schema = @Schema(implementation = ContributorDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    @PutMapping("/{email}")
    public ResponseEntity<ContributorDto> updateContributor(
            @PathVariable String email,
            @Valid @RequestBody ContributorDto contributorDto
    ) throws ContributorBadRequestException, ContributorNotFoundException {
        try {
            ContributorDto updatedContributor = contributorService.updateContributor(email, contributorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedContributor);
        } catch (ContributorBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("X-Error-Message", e.getMessage())
                    .build();
        } catch (ContributorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }
    
    @Operation(
        summary = "Eliminar colaborador",
        description = "Elimina un colaborador del sistema mediante su email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "Colaborador eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Colaborador no encontrado")
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteContributor(@PathVariable String email) throws ContributorNotFoundException {
        try {
            contributorService.deleteContributor(email);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (ContributorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }
}
