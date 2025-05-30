package lk.ijse.travel_booking_system.controller;

import lk.ijse.travel_booking_system.Security.Responce.JWTAuthResponse;
import lk.ijse.travel_booking_system.Security.Secure.SignIn;
import lk.ijse.travel_booking_system.Security.Secure.SignUp;
import lk.ijse.travel_booking_system.dto.UserDTO;
import lk.ijse.travel_booking_system.service.AuthenticationService;
import lk.ijse.travel_booking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> signIN(@RequestBody SignIn signIn){
        System.out.println("sign in unaaa");
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }
    @PostMapping("/signUp")
    public ResponseEntity<JWTAuthResponse> saveUser(@RequestBody SignUp signUp){
        return ResponseEntity.ok(authenticationService.signUp(signUp));
    }



  /*  @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {

        return ResponseEntity.ok(authenticationService.forgotPassword(email));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return ResponseEntity.ok(authenticationService.verifyOtp(email, otp));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        return ResponseEntity.ok(authenticationService.resetPassword(email, newPassword));
}
*/
}
