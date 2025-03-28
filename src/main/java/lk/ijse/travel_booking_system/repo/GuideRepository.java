package lk.ijse.travel_booking_system.repo;

import lk.ijse.travel_booking_system.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, Long> {
}
