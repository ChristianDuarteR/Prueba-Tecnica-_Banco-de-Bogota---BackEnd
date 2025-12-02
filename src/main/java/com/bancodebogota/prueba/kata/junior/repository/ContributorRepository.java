package com.bancodebogota.prueba.kata.junior.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bancodebogota.prueba.kata.junior.model.ContributorModel;

@Repository
public interface ContributorRepository extends JpaRepository<ContributorModel, String> {

    Optional<ContributorModel> findByEmail(String email);
    
}
