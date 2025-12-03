package com.bancodebogota.prueba.kata.junior.service.imp;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;
import com.bancodebogota.prueba.kata.junior.repository.ContributorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailScheduleService {

    @Autowired
    private ContributorRepository contributorRepository;

    @Autowired
    private EmailService emailService;

    @Value("${notification.days-before:7}")
    private int daysBefore;

    @Value("${notification.enabled:true}")
    private boolean notificationsEnabled;

    @Scheduled(cron = "${notification.cron:0 0 9 * * ?}")
    public void checkUpcomingOnboardings() {
        checkUpcomingOnboardings(false);
    }

    public void checkUpcomingOnboardings(boolean isManual) {
        if (!notificationsEnabled) {
            log.info("Notifications are disabled. Skipping check.");
            return;
        }

        log.info("Starting {} onboarding notification check...", isManual ? "MANUAL" : "AUTOMATIC");

        LocalDate today = LocalDate.now();

        List<ContributorModel> contributors = contributorRepository.findAll();
        int emailsSent = 0;

        for (ContributorModel contributor : contributors) {
            if (contributor.getOnboardings() != null) {
                for (OnboardingModel onboarding : contributor.getOnboardings()) {

                    if (!onboarding.getOnBoardingStatus()) {

                        LocalDate onboardingDate =
                            onboarding.getOnBoardingTechnicalDateAssignedate();

                        long daysUntil = ChronoUnit.DAYS.between(today, onboardingDate);

                        boolean shouldSend = isManual
                            ? (daysUntil <= daysBefore && daysUntil >= 0)
                            : (daysUntil == daysBefore);

                        if (shouldSend) {
                            emailService.sendOnboardingReminder(
                                contributor,
                                onboarding,
                                (int) daysUntil
                            );
                            emailsSent++;
                        }
                    }
                }
            }
        }

        log.info("Notification check completed ({}). {} emails sent.",
            isManual ? "MANUAL" : "AUTOMATIC",
            emailsSent
        );
    }
}