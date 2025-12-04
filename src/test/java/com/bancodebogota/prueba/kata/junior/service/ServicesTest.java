package com.bancodebogota.prueba.kata.junior.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;
import com.bancodebogota.prueba.kata.junior.dto.OnBoardingDto;
import com.bancodebogota.prueba.kata.junior.exception.ContributorAlreadyExistsException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorNotFoundException;
import com.bancodebogota.prueba.kata.junior.exception.OnBoardingBowFoundException;
import com.bancodebogota.prueba.kata.junior.exception.OnboardingNotFoundException;
import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;
import com.bancodebogota.prueba.kata.junior.repository.ContributorRepository;
import com.bancodebogota.prueba.kata.junior.repository.OnBoardingRepository;
import com.bancodebogota.prueba.kata.junior.service.imp.ContributorService;
import com.bancodebogota.prueba.kata.junior.service.imp.OnBoardingService;
import com.bancodebogota.prueba.kata.junior.type.Type;

@ExtendWith(MockitoExtension.class)
class ServicesTest {

    // ==================== CONTRIBUTOR SERVICE TESTS ====================
    
    @Nested
    @DisplayName("ContributorService Tests")
    class ContributorServiceTests {
        
        @Mock
        private ContributorRepository contributorRepository;
        
        @Mock
        private OnBoardingService onboardingService;
        
        @InjectMocks
        private ContributorService contributorService;
        
        private ContributorModel contributorModel;
        private ContributorDto contributorDto;
        
        @BeforeEach
        void setUp() {
            contributorModel = new ContributorModel();
            contributorModel.setContributorId("123");
            contributorModel.setEmail("test@example.com");
            contributorModel.setFirstName("John");
            contributorModel.setLastName("Doe");
            contributorModel.setJoinDate(LocalDate.now());
            contributorModel.setOnboardings(new ArrayList<>());
            
            contributorDto = new ContributorDto();
            contributorDto.setEmail("test@example.com");
            contributorDto.setFirstName("John");
            contributorDto.setLastName("Doe");
            contributorDto.setJoinDate(LocalDate.now());
        }
        
        @Test
        @DisplayName("getAllContributors - debe retornar lista de contributors")
        void testGetAllContributors() {
            List<ContributorModel> contributors = List.of(contributorModel);
            when(contributorRepository.findAll()).thenReturn(contributors);
            
            Iterable<ContributorDto> result = contributorService.getAllContributors();
            
            assertNotNull(result);
            assertEquals(1, ((List<ContributorDto>) result).size());
            verify(contributorRepository, times(1)).findAll();
        }
        
        @Test
        @DisplayName("getContributor - debe retornar contributor por email")
        void testGetContributor() {
            when(contributorRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(contributorModel));
            
            ContributorDto result = contributorService.getContributor("test@example.com");
            
            assertNotNull(result);
            assertEquals("test@example.com", result.getEmail());
            verify(contributorRepository, times(1)).findByEmail("test@example.com");
        }
        
        @Test
        @DisplayName("getContributor - debe lanzar excepción si no existe")
        void testGetContributorNotFound() {
            when(contributorRepository.findByEmail("notfound@example.com"))
                .thenReturn(Optional.empty());
            
            assertThrows(ContributorNotFoundException.class, 
                () -> contributorService.getContributor("notfound@example.com"));
        }
        
        @Test
        @DisplayName("createContributor - debe crear contributor exitosamente")
        void testCreateContributor() {
            when(contributorRepository.findByEmail(anyString())).thenReturn(Optional.empty());
            when(contributorRepository.save(any(ContributorModel.class))).thenReturn(contributorModel);
            
            ContributorDto result = contributorService.createContributor(contributorDto);
            
            assertNotNull(result);
            verify(contributorRepository, times(1)).save(any(ContributorModel.class));
        }
        
        @Test
        @DisplayName("createContributor - debe lanzar excepción si ya existe")
        void testCreateContributorAlreadyExists() {
            when(contributorRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(contributorModel));
            
            assertThrows(ContributorAlreadyExistsException.class, 
                () -> contributorService.createContributor(contributorDto));
        }
        
        @Test
        @DisplayName("updateContributor - debe actualizar contributor exitosamente")
        void testUpdateContributor() {
            when(contributorRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(contributorModel));
            when(contributorRepository.findById("123"))
                .thenReturn(Optional.of(contributorModel));
            when(contributorRepository.save(any(ContributorModel.class)))
                .thenReturn(contributorModel);
            
            ContributorDto result = contributorService.updateContributor("test@example.com", contributorDto);
            
            assertNotNull(result);
            verify(contributorRepository, times(1)).save(any(ContributorModel.class));
        }
        
        @Test
        @DisplayName("updateContributor - debe actualizar con onboardings")
        void testUpdateContributorWithOnboardings() {
            OnBoardingDto onboardingDto = new OnBoardingDto();
            onboardingDto.setTitle("Test Onboarding");
            contributorDto.setOnboardings(List.of(onboardingDto));
            
            when(contributorRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(contributorModel));
            when(contributorRepository.findById("123"))
                .thenReturn(Optional.of(contributorModel));
            
            contributorService.updateContributor("test@example.com", contributorDto);
            
            verify(onboardingService, times(1)).updateFields(anyString(), any(OnBoardingDto.class));
        }
        
        @Test
        @DisplayName("deleteContributor - debe eliminar contributor exitosamente")
        void testDeleteContributor() {
            when(contributorRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(contributorModel));
            
            contributorService.deleteContributor("test@example.com");
            
            verify(contributorRepository, times(1)).delete(contributorModel);
        }
        
        @Test
        @DisplayName("deleteContributor - debe lanzar excepción si no existe")
        void testDeleteContributorNotFound() {
            when(contributorRepository.findByEmail("notfound@example.com"))
                .thenReturn(Optional.empty());
            
            assertThrows(ContributorNotFoundException.class, 
                () -> contributorService.deleteContributor("notfound@example.com"));
        }
    }


    // ==================== ONBOARDING SERVICE TESTS ====================
    
    @Nested
    @DisplayName("OnBoardingService Tests")
    class OnBoardingServiceTests {
        
        @Mock
        private OnBoardingRepository onboardingRepository;
        
        @Mock
        private ContributorRepository contributorRepository;
        
        @InjectMocks
        private OnBoardingService onboardingService;
        
        private ContributorModel contributor;
        private OnBoardingDto onboardingDto;
        private OnboardingModel onboardingModel;
        
        @BeforeEach
        void setUp() {
            contributor = new ContributorModel();
            contributor.setContributorId("123");
            contributor.setEmail("test@example.com");
            contributor.setOnboardings(new ArrayList<>());
            
            onboardingDto = new OnBoardingDto();
            onboardingDto.setTitle("Technical Onboarding");
            onboardingDto.setContributorId("123");
            onboardingDto.setType(Type.TECNICO);
            onboardingDto.setOnBoardingTechnicalDateAssigned(LocalDate.now().plusDays(30));
            
            onboardingModel = new OnboardingModel();
            onboardingModel.setId("onb-123");
            onboardingModel.setTitle("Technical Onboarding");
            onboardingModel.setType(Type.TECNICO);
        }
        
        @Test
        @DisplayName("createOnboardingForContributor - debe crear onboarding exitosamente")
        void testCreateOnboardingForContributor() {
            when(contributorRepository.save(any(ContributorModel.class))).thenReturn(contributor);
            when(onboardingRepository.save(any(OnboardingModel.class))).thenReturn(onboardingModel);
            
            onboardingService.createOnboardingForContributor(contributor, onboardingDto);
            
            verify(contributorRepository, times(1)).save(contributor);
            verify(onboardingRepository, times(1)).save(any(OnboardingModel.class));
            assertEquals(1, contributor.getOnboardings().size());
        }
        
        @Test
        @DisplayName("updateFields - debe actualizar onboarding existente")
        void testUpdateFieldsExisting() {
            onboardingDto.setId("onb-123");
            when(onboardingRepository.findById("123")).thenReturn(Optional.of(onboardingModel));
            when(onboardingRepository.save(any(OnboardingModel.class))).thenReturn(onboardingModel);
            
            onboardingService.updateFields("123", onboardingDto);
            
            verify(onboardingRepository, times(1)).findById("123");
            verify(onboardingRepository, times(1)).save(onboardingModel);
        }
        
        @Test
        @DisplayName("updateFields - debe crear nuevo onboarding si no existe ID")
        void testUpdateFieldsNew() {
            onboardingDto.setId(null);
            when(onboardingRepository.save(any(OnboardingModel.class))).thenReturn(onboardingModel);
            
            onboardingService.updateFields("123", onboardingDto);
            
            verify(onboardingRepository, never()).findById(anyString());
            verify(onboardingRepository, times(1)).save(any(OnboardingModel.class));
        }
        
        @Test
        @DisplayName("updateFields - debe lanzar excepción si onboarding no encontrado")
        void testUpdateFieldsNotFound() {
            onboardingDto.setId("onb-123");
            when(onboardingRepository.findById("123")).thenReturn(Optional.empty());
            
            assertThrows(OnBoardingBowFoundException.class, 
                () -> onboardingService.updateFields("123", onboardingDto));
        }
        
        @Test
        @DisplayName("getAllOnBoardings - debe retornar lista de onboardings")
        void testGetAllOnBoardings() {
            List<OnboardingModel> onboardings = List.of(onboardingModel);
            when(onboardingRepository.findAll()).thenReturn(onboardings);
            
            List<OnBoardingDto> result = onboardingService.getAllOnBoardings();
            
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(onboardingRepository, times(1)).findAll();
        }
        
        @Test
        @DisplayName("getAllOnBoardings - debe lanzar excepción si lista vacía")
        void testGetAllOnBoardingsEmpty() {
            when(onboardingRepository.findAll()).thenReturn(List.of());
            
            assertThrows(OnboardingNotFoundException.class, 
                () -> onboardingService.getAllOnBoardings());
        }
    }
}
