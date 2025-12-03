package com.bancodebogota.prueba.kata.junior.service;

import org.springframework.stereotype.Service;

import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;

@Service
public interface ContributorServiceI {

    public Iterable<ContributorDto> getAllContributors();
    
    public ContributorDto getContributor(String id);

    public ContributorDto createContributor(ContributorDto contributorDto);

    public ContributorDto updateContributor(String id, ContributorDto contributorDto);

    public void deleteContributor(String id);
}
