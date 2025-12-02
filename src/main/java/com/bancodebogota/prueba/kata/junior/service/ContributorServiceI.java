package com.bancodebogota.prueba.kata.junior.service;

import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;

public interface ContributorServiceI {

    public Iterable<ContributorDto> getAllContributors();
    
    public ContributorDto getContributor(String id);

    public ContributorDto createContributor(ContributorDto contributorDto);

    public ContributorDto updateContributor(String id, ContributorDto contributorDto);

    public void deleteContributor(String id);
}
