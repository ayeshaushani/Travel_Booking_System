package lk.ijse.travel_booking_system.repo;

import lk.ijse.travel_booking_system.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
