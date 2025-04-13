package lk.ijse.travel_booking_system.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lk.ijse.travel_booking_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    @Override
    public void sendBookingConfirmationHtml(String toEmail, String customerName, String bookingDetails) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("bookings@travelsystem.com");
            helper.setTo(toEmail);
            helper.setSubject("Your Booking Confirmation");

            String htmlMsg = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                    ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                    ".header { background-color: #4361ee; color: white; padding: 20px; text-align: center; }" +
                    ".content { padding: 20px; }" +
                    ".details { background-color: #f8f9fa; padding: 15px; border-radius: 5px; }" +
                    ".footer { margin-top: 20px; font-size: 0.9em; color: #666; text-align: center; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<div class='header'>" +
                    "<h2>Booking Confirmation</h2>" +
                    "</div>" +
                    "<div class='content'>" +
                    "<p>Dear " + ",</p>" +
                    "<p>Thank you for booking with us! Here are your reservation details:</p>" +
                    "<div class='details'>" +
                    "<pre>" + bookingDetails + "</pre>" +
                    "</div>" +
                    "<p>We're looking forward to serving you. If you have any questions, please contact our support team.</p>" +
                    "</div>" +
                    "<div class='footer'>" +
                    "<p>Best regards,<br><strong>Travel Booking System Team</strong></p>" +
                    "<p>📞 +1 (555) 123-4567<br>✉️ support@travelsystem.com</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

}