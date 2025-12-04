package com.bancodebogota.prueba.kata.junior.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import com.bancodebogota.prueba.kata.junior.dto.ContributorDto;
import com.bancodebogota.prueba.kata.junior.dto.OnBoardingDto;
import com.bancodebogota.prueba.kata.junior.exception.ContributorAlreadyExistsException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorBadRequestException;
import com.bancodebogota.prueba.kata.junior.exception.ContributorNotFoundException;
import com.bancodebogota.prueba.kata.junior.exception.OnboardingNotFoundException;
import com.bancodebogota.prueba.kata.junior.service.imp.ContributorService;
import com.bancodebogota.prueba.kata.junior.service.imp.EmailScheduleService;
import com.bancodebogota.prueba.kata.junior.service.imp.EmailService;
import com.bancodebogota.prueba.kata.junior.service.imp.OnBoardingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestController {

    private MockMvc mockMvc;

    @Mock
    private OnBoardingService onBoardingService;

    @Mock
    private EmailScheduleService emailScheduleService;

    @Mock
    private EmailService emailService;

    @Mock
    private ContributorService contributorService;

    @InjectMocks
    private OnBoardingController onBoardingController;

    @InjectMocks
    private EmailScheduleController emailScheduleController;

    @InjectMocks
    private ContributorController contributorController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(
                onBoardingController,
                emailScheduleController,
                contributorController
        ).build();
    }

    // ========================= OnBoardingController Tests =========================
    @Test
    void testGetOnboardings_Success() throws Exception {
        OnBoardingDto dto1 = new OnBoardingDto();
        OnBoardingDto dto2 = new OnBoardingDto();

        when(onBoardingService.getAllOnBoardings()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get("/api/v1/onboardings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(onBoardingService, times(1)).getAllOnBoardings();
    }

    @Test
    void testGetOnboardings_NotFound() throws Exception {
        when(onBoardingService.getAllOnBoardings()).thenThrow(new OnboardingNotFoundException(""));

        mockMvc.perform(get("/api/v1/onboardings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().string("X-Error-Message", "No se encontraron onboardings"));

        verify(onBoardingService, times(1)).getAllOnBoardings();
    }

    // ========================= EmailScheduleController Tests =========================
    @Test
    void testGetStatus() throws Exception {
        mockMvc.perform(get("/api/v1/emails")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationsEnabled").value(true))
                .andExpect(jsonPath("$.daysBefore").value(7))
                .andExpect(jsonPath("$.schedule").value("Todos los días a las 9:00 AM"));
    }

    @Test
    void testTriggerNotifications_Manual() throws Exception {
        mockMvc.perform(post("/api/v1/emails")
                .param("manual", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Manual notification check triggered successfully"))
                .andExpect(jsonPath("$.status").value("ok"));

        verify(emailScheduleService, times(1)).checkUpcomingOnboardings(true);
    }

    @Test
    void testTriggerNotifications_Automatic() throws Exception {
        mockMvc.perform(post("/api/v1/emails")
                .param("manual", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Automatic notification triggered successfully"))
                .andExpect(jsonPath("$.status").value("ok"));

        verify(emailScheduleService, times(1)).checkUpcomingOnboardings(false);
    }

    // ========================= ContributorController Tests =========================
    @Test
    void testGetContributors_Success() throws Exception {
        ContributorDto dto1 = new ContributorDto();
        ContributorDto dto2 = new ContributorDto();

        when(contributorService.getAllContributors()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get("/api/v1/contributors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(contributorService, times(1)).getAllContributors();
    }

    @Test
    void testGetContributors_NotFound() throws Exception {
        when(contributorService.getAllContributors()).thenThrow(new ContributorNotFoundException("No contributors"));

        mockMvc.perform(get("/api/v1/contributors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(contributorService, times(1)).getAllContributors();
    }

    @Test
    void testGetContributor_Success() throws Exception {
        ContributorDto dto = new ContributorDto();
        dto.setEmail("test@example.com");

        when(contributorService.getContributor("test@example.com")).thenReturn(dto);

        mockMvc.perform(get("/api/v1/contributors/test@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetContributor_NotFound() throws Exception {
        when(contributorService.getContributor("notfound@example.com"))
                .thenThrow(new ContributorNotFoundException("notfound@example.com"));

        mockMvc.perform(get("/api/v1/contributors/notfound@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().string("X-Error-Message", "Contributor not found with ID: notfound@example.com"));
    }

    @Test
    void testCreateContributor_Success() throws Exception {
        ContributorDto dto = new ContributorDto();
        dto.setEmail("new@example.com");

        when(contributorService.createContributor(any(ContributorDto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/contributors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    @Test
    void testCreateContributor_AlreadyExists() throws Exception {
        ContributorDto dto = new ContributorDto();
        dto.setEmail("exists@example.com");

        when(contributorService.createContributor(any(ContributorDto.class)))
                .thenThrow(new ContributorAlreadyExistsException("exists@example.com"));

        mockMvc.perform(post("/api/v1/contributors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("X-Error-Message", "Contributor already exists with email: exists@example.com"));
    }

    @Test
    void testUpdateContributor_Success() throws Exception {
        ContributorDto dto = new ContributorDto();
        dto.setEmail("update@example.com");

        when(contributorService.updateContributor(eq("update@example.com"), any(ContributorDto.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/v1/contributors/update@example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("update@example.com"));
    }

    @Test
    void testUpdateContributor_BadRequest() throws Exception {
        ContributorDto dto = new ContributorDto();

        when(contributorService.updateContributor(eq("bad@example.com"), any(ContributorDto.class)))
                .thenThrow(new ContributorBadRequestException("Invalid data"));

        mockMvc.perform(put("/api/v1/contributors/bad@example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("X-Error-Message", "Campo: Invalid data es invalido o esta incompleto."));
    }

    @Test
    void testUpdateContributor_NotFound() throws Exception {
        ContributorDto dto = new ContributorDto();

        when(contributorService.updateContributor(eq("notfound@example.com"), any(ContributorDto.class)))
                .thenThrow(new ContributorNotFoundException("notfound@example.com"));

        mockMvc.perform(put("/api/v1/contributors/notfound@example.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(header().string("X-Error-Message", "Contributor not found with ID: notfound@example.com"));
    }

    @Test
    void testDeleteContributor_Success() throws Exception {
        doNothing().when(contributorService).deleteContributor("delete@example.com");

        mockMvc.perform(delete("/api/v1/contributors/delete@example.com"))
                .andExpect(status().isAccepted());

        verify(contributorService, times(1)).deleteContributor("delete@example.com");
    }

    @Test
    void testDeleteContributor_NotFound() throws Exception {
        doThrow(new ContributorNotFoundException("missing@example.com"))
                .when(contributorService).deleteContributor("missing@example.com");

        mockMvc.perform(delete("/api/v1/contributors/missing@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(header().string("X-Error-Message", "Contributor not found with ID: missing@example.com"));
    }
}