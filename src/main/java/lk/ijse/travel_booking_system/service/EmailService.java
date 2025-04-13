package lk.ijse.travel_booking_system.service;

public interface EmailService {
    void sendWelcomeEmail(String toEmail, String name);

    void sendBookingConfirmationHtml(String toEmail, String customerName, String bookingDetails);
}
