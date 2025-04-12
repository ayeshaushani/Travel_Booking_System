package lk.ijse.travel_booking_system.service.impl;

import lk.ijse.travel_booking_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendWelcomeEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("travelbookingsystem@example.com");
        message.setTo(toEmail);
        message.setSubject("Welcome to Travel Booking System");
        message.setText(
            "Dear " + name + ",\n\n" +
            "You have been successfully registered as a guide in our Travel Booking System.\n\n" +
            "We look forward to working with you!\n\n" +
            "Best regards,\n" +
            "Travel Booking System Team"
        );
        
        mailSender.send(message);
    }
}