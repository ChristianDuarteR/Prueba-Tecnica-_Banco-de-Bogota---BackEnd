package com.bancodebogota.prueba.kata.junior.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bancodebogota.prueba.kata.junior.model.ContributorModel;
import com.bancodebogota.prueba.kata.junior.model.OnboardingModel;
import com.bancodebogota.prueba.kata.junior.service.EmailServiceI;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService implements EmailServiceI{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${notification.enabled:true}")
    private boolean notificationsEnabled;

    @Override
    public void sendOnboardingReminder(ContributorModel contributor, OnboardingModel onboarding, int daysRemaining) {
        if (!notificationsEnabled) {
            log.info("Notifications are disabled. Skipping email for {}", contributor.getEmail());
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(contributor.getEmail());
            helper.setSubject(onboarding.getTitle());

            Context context = new Context();
            context.setVariable("contributorName", contributor.getFirstName() + " " + contributor.getLastName());
            context.setVariable("onboardingType", onboarding.getType().toString());
            context.setVariable("dueDate", onboarding.getOnBoardingTechnicalDateAssignedate());
            context.setVariable("daysRemaining", daysRemaining);

            String htmlContent = templateEngine.process("email/onboarding-reminder", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email reminder sent to {} for {} onboarding", contributor.getEmail(), onboarding.getTitle());

        } catch (MessagingException e) {
            log.error("Error sending email to {}: {}", contributor.getEmail(), e.getMessage(), e);
        }
    }

}
