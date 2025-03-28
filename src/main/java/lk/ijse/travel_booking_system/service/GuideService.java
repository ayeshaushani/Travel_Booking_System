package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.GuideDTO;
import java.util.List;

public interface GuideService {
    GuideDTO saveGuide(GuideDTO guideDTO);
    List<GuideDTO> getAllGuides();
    GuideDTO getGuideById(Long id);
    GuideDTO updateGuide(Long id, GuideDTO guideDTO);
    void deleteGuide(Long id);
}
