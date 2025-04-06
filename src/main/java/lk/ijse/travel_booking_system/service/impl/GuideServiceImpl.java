package lk.ijse.travel_booking_system.service.impl;

import lk.ijse.travel_booking_system.dto.GuideDTO;
import lk.ijse.travel_booking_system.entity.Guide;
import lk.ijse.travel_booking_system.Exception.NotFoundException;
import lk.ijse.travel_booking_system.repo.GuideRepository;
import lk.ijse.travel_booking_system.service.GuideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GuideServiceImpl implements GuideService {

    private final GuideRepository guideRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GuideServiceImpl(GuideRepository guideRepository, ModelMapper modelMapper) {
        this.guideRepository = guideRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GuideDTO saveGuide(GuideDTO guideDTO) {
        // Validate input
        if (guideDTO == null) {
            throw new IllegalArgumentException("GuideDTO cannot be null");
        }

        // Convert DTO to entity
        Guide guide = modelMapper.map(guideDTO, Guide.class);

        // Save to database
        Guide savedGuide = guideRepository.save(guide);

        // Convert back to DTO and return
        return modelMapper.map(savedGuide, GuideDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuideDTO> getAllGuides() {
        return guideRepository.findAll()
                .stream()
                .map(guide -> modelMapper.map(guide, GuideDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GuideDTO getGuideById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Guide ID cannot be null");
        }

        Guide guide = guideRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Guide not found with id: " + id));

        return modelMapper.map(guide, GuideDTO.class);
    }

    @Override
    public GuideDTO updateGuide(Long id, GuideDTO guideDTO) {
        if (id == null || guideDTO == null) {
            throw new IllegalArgumentException("ID and GuideDTO cannot be null");
        }

        // Check if guide exists
        Guide existingGuide = guideRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Guide not found with id: " + id));

        // Update fields
        existingGuide.setName(guideDTO.getName());
        existingGuide.setContact(guideDTO.getContact());
        existingGuide.setEmail(guideDTO.getEmail());
        existingGuide.setExperience(guideDTO.getExperience());

        // Save updated guide
        Guide updatedGuide = guideRepository.save(existingGuide);

        return modelMapper.map(updatedGuide, GuideDTO.class);
    }

    @Override
    public void deleteGuide(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Guide ID cannot be null");
        }

        try {
            guideRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Guide not found with id: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return guideRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuideDTO> getGuidesByExperienceGreaterThan(int years) {
        return guideRepository.findByExperienceGreaterThan(years)
                .stream()
                .map(guide -> modelMapper.map(guide, GuideDTO.class))
                .collect(Collectors.toList());
    }
}