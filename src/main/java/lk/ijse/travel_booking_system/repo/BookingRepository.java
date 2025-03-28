package lk.ijse.travel_booking_system.repo;

import lk.ijse.travel_booking_system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
