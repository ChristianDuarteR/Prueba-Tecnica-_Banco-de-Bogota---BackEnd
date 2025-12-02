package com.bancodebogota.prueba.kata.junior.service;

import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;

public interface ContributorServiceI {

    public Iterable<ContributorDto> getAllContributors();
    
    public ContributorDto getContributorDto(Long id);

    public ContributorDto createContributor(ContributorDto contributorDto);

    public ContributorDto updateContributor(Long id, ContributorDto contributorDto);

    public void deleteContributor(Long id);
}
