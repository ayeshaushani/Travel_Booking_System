package lk.ijse.travel_booking_system.repo;

import lk.ijse.travel_booking_system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
