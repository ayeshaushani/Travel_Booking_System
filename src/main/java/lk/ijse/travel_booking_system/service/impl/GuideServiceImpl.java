package lk.ijse.travel_booking_system.service.impl;

import lk.ijse.travel_booking_system.dto.GuideDTO;
import lk.ijse.travel_booking_system.entity.Guide;
import lk.ijse.travel_booking_system.repo.GuideRepository;
import lk.ijse.travel_booking_system.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuideServiceImpl implements GuideService {

    @Autowired
    private GuideRepository guideRepository;

    @Override
    public GuideDTO saveGuide(GuideDTO guideDTO) {
        Guide guide = new Guide(
                guideDTO.getGuideId(),
                guideDTO.getName(),
                guideDTO.getContact(),
                guideDTO.getExperience(),
                null // TravelPackage ekak naththam null karanna
        );
        guide = guideRepository.save(guide);

        return new GuideDTO(
                guide.getGuideId(),
                guide.getName(),
                guide.getContact(),
                guide.getExperience()
        );
    }

    @Override
    public List<GuideDTO> getAllGuides() {
        return guideRepository.findAll().stream().map(guide -> new GuideDTO(
                guide.getGuideId(),
                guide.getName(),
                guide.getContact(),
                guide.getExperience()
        )).collect(Collectors.toList());
    }

    @Override
    public GuideDTO getGuideById(Long id) {
        Guide guide = guideRepository.findById(id).orElseThrow(() -> new RuntimeException("Guide not found"));
        return new GuideDTO(
                guide.getGuideId(),
                guide.getName(),
                guide.getContact(),
                guide.getExperience()
        );
    }

    @Override
    public GuideDTO updateGuide(Long id, GuideDTO guideDTO) {
        Guide guide = guideRepository.findById(id).orElseThrow(() -> new RuntimeException("Guide not found"));

        guide.setName(guideDTO.getName());
        guide.setContact(guideDTO.getContact());
        guide.setExperience(guideDTO.getExperience());

        guideRepository.save(guide);

        return new GuideDTO(
                guide.getGuideId(),
                guide.getName(),
                guide.getContact(),
                guide.getExperience()
        );
    }

    @Override
    public void deleteGuide(Long id) {
        guideRepository.deleteById(id);
    }
}
