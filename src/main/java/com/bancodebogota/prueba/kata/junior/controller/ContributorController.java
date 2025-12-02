package com.bancodebogota.prueba.kata.junior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getContributor() throws ContributorNotFoundException {
        return "In contruction...";
    }

    @GetMapping
    public String getContributos() throws ContributorNotFoundException {
        return "In contruction...";
    }

    @PostMapping
    public String createContributor() throws ContributorBadRequestException{
        return "In contruction...";
    }

    @PutMapping
    public String updateContributor() throws ContributorBadRequestException, ContributorNotFoundException {
        return "In contruction...";
    }

    @DeleteMapping
    public String deleteContributor() throws ContributorNotFoundException {
        return "In contruction...";
    }

    
}
