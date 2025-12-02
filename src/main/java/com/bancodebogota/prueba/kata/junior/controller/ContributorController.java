package com.bancodebogota.prueba.kata.junior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;
import com.bancodebogota.prueba.kata.junior.exception.ContributorAlreadyExistsException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorBadRequestException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorNotFoundException;
import com.bancodebogota.prueba.kata.junior.service.imp.ContributorService;

@RestController
@RequestMapping("/api/v1/contributors")
public class ContributorController {

    private final ContributorService contributorService;

    public ContributorController(@Autowired ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @GetMapping
    public ResponseEntity<Iterable<ContributorDto>> getContributors() throws ContributorNotFoundException {
        try{
            Iterable<ContributorDto> contributors = contributorService.getAllContributors();
            return ResponseEntity.ok(contributors);
        } catch (ContributorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

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

    @PostMapping
    public ResponseEntity<ContributorDto> createContributor(@RequestBody ContributorDto contributorDto) throws ContributorBadRequestException{
        try {
            ContributorDto createdContributor = contributorService.createContributor(contributorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContributor);
        } catch (ContributorAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }
    
    @PutMapping("/{email}")
    public ResponseEntity<ContributorDto> updateContributor(@PathVariable String email, @RequestBody ContributorDto contributorDto) throws ContributorBadRequestException, ContributorNotFoundException {
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

    
