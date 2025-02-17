package com.placidotech.pasteleria.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String fromEmail;

    @Value("${app.url.base}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String recipientEmail, String token){
        String subject = "Activa tu cuenta";
        String content = String.format(
            "Hola,<br><br>Por favor activa tu cuenta haciendo clic en el siguiente enlace:<br>"
                    + "<a href=\\\"%s/activate?token=%s\\\">Activar cuenta</a><br><br>Gracias.", 
            baseUrl, token
        );
        sendEmail(recipientEmail, subject, content);
    }

    public void sendPasswordResetEmail(String recipientEmail, String token) {
        String subject = "Restablecer contraseña";
        String content = String.format(
                "Hola,<br><br>Para restablecer tu contraseña, haz clic en el siguiente enlace:<br>"
                        + "<a href=\"%s/reset-password?token=%s\">Restablecer contraseña</a><br><br>Si no solicitaste esto, ignora este mensaje.",
                baseUrl, token
        );
        sendEmail(recipientEmail, subject, content);
    }

    public void sendEmail(String recipientEmail, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo electrónico a " + recipientEmail, e);
        }
    }

    public void sendVerificationEmail(String to, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirmación de Cambio de Correo");
        message.setText("Tu código de verificación es: " + verificationCode);
        mailSender.send(message);
    }
}
