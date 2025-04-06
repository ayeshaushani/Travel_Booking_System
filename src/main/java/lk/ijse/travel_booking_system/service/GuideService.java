package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.GuideDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GuideService {
    GuideDTO saveGuide(GuideDTO guideDTO);
    List<GuideDTO> getAllGuides();
    GuideDTO getGuideById(Long id);
    GuideDTO updateGuide(Long id, GuideDTO guideDTO);
    void deleteGuide(Long id);

    @Transactional(readOnly = true)
    boolean existsByEmail(String email);

    @Transactional(readOnly = true)
    List<GuideDTO> getGuidesByExperienceGreaterThan(int years);
}
