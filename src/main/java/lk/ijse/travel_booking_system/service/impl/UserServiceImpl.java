package lk.ijse.travel_booking_system.service.impl;

import lk.ijse.travel_booking_system.dto.UserDTO;
import lk.ijse.travel_booking_system.dto.UserWithKey;
import lk.ijse.travel_booking_system.entity.User;
import lk.ijse.travel_booking_system.repo.UserRepository;
import lk.ijse.travel_booking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import lk.ijse.travel_booking_system.Exception.NotFoundException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
@Autowired
private  UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username ->
                userRepository.findByEmail(username).
                        orElseThrow(()->new NotFoundException("User Name Not Found"));

    }

    @Override
    public boolean sendCodeToChangePassword(UserWithKey userWithKey) {
        Optional<User> byEmail=userRepository.findByEmail((userWithKey.getEmail()));
        if (byEmail.isPresent()){
            return true;
        }
        return false;

    }
}
