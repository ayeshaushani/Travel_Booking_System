package lk.ijse.travel_booking_system.controller;

import lk.ijse.travel_booking_system.entity.User;
import lk.ijse.travel_booking_system.repo.UserRepository;
import lk.ijse.travel_booking_system.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> user = userRepository.findByEmail(request.get("email"));

        if (user.isPresent() && user.get().getPassword().equals(request.get("password"))) {
            response.put("success", true);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            // Log additional details for debugging
            System.out.println("User not found or password incorrect.");

            otpService.sendOTP(request.get("email"));
            response.put("success", false);
            response.put("message", "Incorrect password. OTP sent to email.");
            return ResponseEntity.status(403).body(response); // Ensure this returns a valid JSON response
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOTP(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        if (otpService.validateOTP(request.get("email"), request.get("otp"))) {
            response.put("success", true);
            response.put("message", "OTP verified! You can now log in.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid OTP.");
            return ResponseEntity.status(400).body(response);
        }
    }
}
