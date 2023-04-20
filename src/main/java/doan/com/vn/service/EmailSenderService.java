package doan.com.vn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(String fromEmail,
            String subject,
            String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo("hieugiang2k1@gmail.com");
        message.setText(body);
        message.setSubject(subject);
        
        mailSender.send(message);
    }
    
}
