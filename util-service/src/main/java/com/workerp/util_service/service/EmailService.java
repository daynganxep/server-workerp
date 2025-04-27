package com.workerp.util_service.service;

import com.workerp.common_lib.dto.message.EmailMessage;
import com.workerp.common_lib.exception.AppException;
import com.workerp.util_service.dto.SendEmailDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String systemEmail;

    @Value("${app.domain}")
    private String appDomain;

    public void sendEmail(SendEmailDto emailPayload) {
        var message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailPayload.getTo());
            helper.setSubject(emailPayload.getSubject());
            helper.setText(emailPayload.getText(), true);
            helper.setFrom(systemEmail);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Failed to send email", "mail-e-01");
        }
    }

    private String loadEmailTemplate(String filePath, Map<String, String> placeholders) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            byte[] fileData = StreamUtils.copyToByteArray(resource.getInputStream());
            String content = new String(fileData, StandardCharsets.UTF_8);
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String replacement = entry.getValue() != null ? entry.getValue() : "";
                content = content.replace("{{" + entry.getKey() + "}}", replacement);
            }
            return content;
        } catch (IOException e) {
            return "";
        }
    }

    public void sendVerificationEmail(EmailMessage emailMessage) {
        String code = emailMessage.getValues().getOrDefault("code", "123456");
        Map<String, String> placeholders = Map.of("verifyUrl", String.format("%s/auth/register/verify/%s", appDomain,code));
        String content = loadEmailTemplate("HTMLTemplates/register.html", placeholders);
        sendEmail(SendEmailDto.builder().to(emailMessage.getTo()).subject("(WORK.ERP) Xác nhận đăng ký").text(content).build());
    }

    public void sendWelcomeEmail(EmailMessage emailMessage) {
        String content = loadEmailTemplate("HTMLTemplates/welcome.html", emailMessage.getValues());
        sendEmail(SendEmailDto.builder().to(emailMessage.getTo()).subject("(WORK.ERP) Chào mừng đến với WORK.ERP").text(content).build());
    }

    public void sendVerificationForgotPasswordEmail(EmailMessage emailMessage) {
        String content = loadEmailTemplate("HTMLTemplates/forget-password.html", emailMessage.getValues());
        sendEmail(SendEmailDto.builder().to(emailMessage.getTo()).subject("(WORK.ERP) Khôi phục mật khẩu").text(content).build());
    }

    public void sendInviteToCompanyEmail(EmailMessage emailMessage) {
        String code = emailMessage.getValues().getOrDefault("code", "123456");
        emailMessage.getValues().put("verifyUrl", String.format("%s/api/hr-app/employees/invite-to-company/verify/%s",appDomain, code));
        String content = loadEmailTemplate("HTMLTemplates/invite-to-company.html", emailMessage.getValues());
        sendEmail(SendEmailDto.builder().to(emailMessage.getTo()).subject("(WORK.ERP) Thư mời tham gia công ty").text(content).build());
    }

    public void sendAddedToProjectEmail(EmailMessage emailMessage) {
        String content = loadEmailTemplate("HTMLTemplates/added-to-project.html", emailMessage.getValues());
        sendEmail(SendEmailDto.builder().to(emailMessage.getTo()).subject("(WORK.ERP) Thông báo thêm vào dự án").text(content).build());
    }
}