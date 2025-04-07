package lk.ijse.travel_booking_system.service;

public interface OTPService {
    void sendOTP(String email);
    boolean validateOTP(String email, String otp);
}