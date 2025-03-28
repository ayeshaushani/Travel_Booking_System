package lk.ijse.travel_booking_system.repo;

import lk.ijse.travel_booking_system.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
