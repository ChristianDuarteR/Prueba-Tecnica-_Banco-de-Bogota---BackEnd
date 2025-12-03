package com.bancodebogota.prueba.kata.junior.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancodebogota.prueba.kata.junior.service.imp.EmailScheduleService;
import com.bancodebogota.prueba.kata.junior.service.imp.EmailService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/emails")
@Tag(name = "emails", description = "Notificador automatico")
public class EmailScheduleController {

    private  EmailScheduleService emailScheduleService;

    public EmailScheduleController(@Autowired EmailScheduleService emailScheduleService,
                                   @Autowired EmailService emailService){

    this.emailScheduleService = emailScheduleService;
    
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("notificationsEnabled", true);
        status.put("daysBefore", 7);
        status.put("schedule", "Todos los dias a las 9:00 AM");
        return ResponseEntity.ok(status);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> triggerNotifications(
            @RequestParam(defaultValue = "false") boolean manual
    ) {
        if (manual) {
            emailScheduleService.checkUpcomingOnboardings(true);;
        }  else{
            emailScheduleService.checkUpcomingOnboardings(false);;
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", manual
            ? "Manual notification check triggered successfully"
            : "Automatic notification triggered successfully");
        response.put("status", "ok");

        return ResponseEntity.ok(response);
    }

}
