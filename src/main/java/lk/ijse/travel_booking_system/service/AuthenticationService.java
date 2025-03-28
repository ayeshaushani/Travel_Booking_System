package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.Security.Responce.JWTAuthResponse;
import lk.ijse.travel_booking_system.Security.Secure.SignIn;
import lk.ijse.travel_booking_system.Security.Secure.SignUp;
import lk.ijse.travel_booking_system.dto.PasswordDto;

public interface AuthenticationService {
    JWTAuthResponse signUp(SignUp signUp);
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse refreshToken(String refreshToken);
    void changePassword(PasswordDto passwordDto);
}
