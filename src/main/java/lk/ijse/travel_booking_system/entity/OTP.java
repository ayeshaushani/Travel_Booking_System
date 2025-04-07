package lk.ijse.travel_booking_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OTP {
    @Id
    private String email;
    private String otp;
    private long timestamp;

    public OTP() {}
    public OTP(String email, String otp, long timestamp) {
        this.email = email;
        this.otp = otp;
        this.timestamp = timestamp;
    }
    public String getEmail() { return email; }
    public String getOtp() { return otp; }
    public long getTimestamp() { return timestamp; }
}