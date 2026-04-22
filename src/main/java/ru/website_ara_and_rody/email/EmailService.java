package ru.website_ara_and_rody.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.website_ara_and_rody.dto.EmailRequestDto;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(EmailRequestDto emailRequestDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequestDto.email());
        message.setSubject("Подтверждении регистрации: ");
        message.setText("Код регистрации: " + emailRequestDto.confirmationCode());
        mailSender.send(message);
    }
}
