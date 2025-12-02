package com.bancodebogota.prueba.kata.junior.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;
import com.bancodebogota.prueba.kata.junior.repository.ContributorRepository;
import com.bancodebogota.prueba.kata.junior.service.ContributorServiceI;

@Service
public class ContributorService implements ContributorServiceI {

    private final ContributorRepository contributorRepository;
    
    public ContributorService(@Autowired ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;

    }

    @Override
    public Iterable<ContributorDto> getAllContributors() {
        try {
            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'getAllContributors'");
        }
    }

    @Override
    public ContributorDto getContributorDto(Long id) {
        try {
            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'getContributorDto'");
            }    
        }

    @Override
    public ContributorDto createContributor(ContributorDto contributorDto) {
        try {
            
        } catch (Exception e) {
        throw new UnsupportedOperationException("Unimplemented method 'createContributor'");
        }
    }


    @Override
    public ContributorDto updateContributor(Long id, ContributorDto contributorDto) {
        try {

        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'updateContributor'");            
        }        
    }

    @Override
    public void deleteContributor(Long id) {
        try {

        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'deleteContributor'");
        }        
    }

    
}
