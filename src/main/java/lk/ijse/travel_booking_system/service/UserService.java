package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.UserWithKey;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    boolean sendCodeToChangePassword(UserWithKey userWithKey);
}
