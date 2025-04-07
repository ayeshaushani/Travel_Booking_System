package lk.ijse.travel_booking_system.service.impl;
import lk.ijse.travel_booking_system.entity.OTP;
import lk.ijse.travel_booking_system.repo.OTPRepository;
import lk.ijse.travel_booking_system.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
@Service
class OTPServiceImpl implements OTPService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OTPRepository otpRepository;

    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    @Override
    public void sendOTP(String email) {
        String otp = generateOTP();
        OTP otpEntity = new OTP(email, otp, System.currentTimeMillis());
        otpRepository.save(otpEntity);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Use this OTP to log in: " + otp);
        mailSender.send(message);
    }

    @Override
    public boolean validateOTP(String email, String otp) {
        Optional<OTP> otpRecord = otpRepository.findByEmail(email);
        if (otpRecord.isPresent()) {
            OTP otpEntity = otpRecord.get();
            long currentTime = System.currentTimeMillis();
            if (otpEntity.getOtp().equals(otp) && (currentTime - otpEntity.getTimestamp()) < 300000) {
                otpRepository.delete(otpEntity);
                return true;
            }
        }
        return false;
    }
}