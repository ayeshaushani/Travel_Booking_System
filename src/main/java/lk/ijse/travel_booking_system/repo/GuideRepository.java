package lk.ijse.travel_booking_system.repo;

import lk.ijse.travel_booking_system.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    boolean existsByEmail(String email);

    Optional<Object> findByExperienceGreaterThan(int years);
}
