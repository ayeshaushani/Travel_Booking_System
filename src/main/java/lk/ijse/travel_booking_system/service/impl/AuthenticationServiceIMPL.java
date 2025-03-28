package lk.ijse.travel_booking_system.service.impl;

import lk.ijse.travel_booking_system.Exception.NotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lk.ijse.travel_booking_system.Security.Responce.JWTAuthResponse;
import lk.ijse.travel_booking_system.Security.Secure.SignIn;
import lk.ijse.travel_booking_system.Security.Secure.SignUp;
import lk.ijse.travel_booking_system.dto.PasswordDto;
import lk.ijse.travel_booking_system.dto.UserDTO;
import lk.ijse.travel_booking_system.entity.User;
import lk.ijse.travel_booking_system.repo.UserRepository;
import lk.ijse.travel_booking_system.service.AuthenticationService;
import lk.ijse.travel_booking_system.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceIMPL implements AuthenticationService {
    private final ModelMapper mapping;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signUp(SignUp signUp) {
        UserDTO userDTO =UserDTO.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .role(signUp.getRole())
                .build();
        User userEntity1 =mapping.map(userDTO, User.class);
        System.out.println(userEntity1);
        userRepository.save(userEntity1);
        System.out.println(userEntity1);
        String generateToken = jwtService.generateToken(userEntity1);
        return JWTAuthResponse.builder().tokens(generateToken).build();
    }

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(),signIn.getPassword())
        );
        User userEntity=userRepository.findByEmail(signIn.getEmail())
                .orElseThrow(()->new NotFoundException("User Not Found"));
        var generateToken =jwtService.generateToken(userEntity);
        System.out.println("==================================  :"+generateToken);
        return JWTAuthResponse.builder().tokens(generateToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String refreshToken) {
        String user =jwtService.extractUserName(refreshToken);
        User findUser =userRepository.findByEmail(user).orElseThrow(()-> new NotFoundException("Couldn't find User"));
        String token =jwtService.refreshToken(findUser);
        return JWTAuthResponse.builder().tokens(token).build();
    }

    @Override
    public void changePassword(PasswordDto passwordDto) {
        Optional<User> byEmail = userRepository.findByEmail(passwordDto.getEmail());
        if (byEmail.isPresent()) {
            User userEntity = userRepository.getReferenceById(byEmail.get().getUserId());
            userEntity.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            userRepository.save(userEntity);
        }
    }
    public String getSignedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Returns the email or username (based on your principal configuration)
        }
        throw new IllegalStateException("No authenticated user found");
    }
}
