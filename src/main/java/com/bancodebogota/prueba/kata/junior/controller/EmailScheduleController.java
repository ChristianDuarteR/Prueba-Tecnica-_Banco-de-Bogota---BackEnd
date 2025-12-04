package com.bancodebogota.prueba.kata.junior.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodebogota.prueba.kata.junior.service.imp.EmailScheduleService;
import com.bancodebogota.prueba.kata.junior.service.imp.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/emails")
@Tag(name = "Emails", description = "Notificador automático de onboarding")
public class EmailScheduleController {

    private final EmailScheduleService emailScheduleService;

    public EmailScheduleController(@Autowired EmailScheduleService emailScheduleService,
                                   @Autowired EmailService emailService) {
        this.emailScheduleService = emailScheduleService;
    }

    /**
     * Obtiene el estado actual del sistema de notificaciones.
     * @return Map con la configuración de notificaciones activas, días antes de aviso y horario
     */
    @Operation(summary = "Estado de notificaciones", description = "Obtiene el estado actual de las notificaciones automáticas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de notificaciones devuelto correctamente")
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("notificationsEnabled", true);
        status.put("daysBefore", 7);
        status.put("schedule", "Todos los días a las 9:00 AM");
        return ResponseEntity.ok(status);
    }

    /**
     * Dispara manual o automáticamente las notificaciones de onboarding.
     * @param manual Indica si la verificación se hace manualmente (true) o automática (false)
     * @return Map con mensaje de resultado y estado
     */
    @Operation(summary = "Disparar notificaciones", description = "Permite disparar manual o automáticamente las notificaciones de onboarding")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación disparada correctamente")
    })
    @PostMapping
    public ResponseEntity<Map<String, String>> triggerNotifications(
            @Parameter(description = "Indica si la verificación es manual (true) o automática (false)", required = false)
            @RequestParam(defaultValue = "false") boolean manual
    ) {
        emailScheduleService.checkUpcomingOnboardings(manual);

        Map<String, String> response = new HashMap<>();
        response.put("message", manual
            ? "Manual notification check triggered successfully"
            : "Automatic notification triggered successfully");
        response.put("status", "ok");

        return ResponseEntity.ok(response);
    }

}
