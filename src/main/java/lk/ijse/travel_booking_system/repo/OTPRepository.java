package lk.ijse.travel_booking_system.repo;


import lk.ijse.travel_booking_system.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, String> {
    Optional<OTP> findByEmail(String email);
}