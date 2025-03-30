package lk.ijse.travel_booking_system.service.impl;

import lk.ijse.travel_booking_system.dto.TravelPackageDTO;
import lk.ijse.travel_booking_system.entity.TravelPackage;
import lk.ijse.travel_booking_system.repo.TravelPackageRepository;
import lk.ijse.travel_booking_system.service.TravelPackageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravelPackageServiceImpl implements TravelPackageService {

    @Autowired
    private TravelPackageRepository travelPackageRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public boolean saveTravelPackage(TravelPackageDTO travelPackageDTO) {

        if (travelPackageRepository.existsById(travelPackageDTO.gettPackageId())){
            return false;
        }else {
            System.out.println("Serive ekat awa"+travelPackageDTO);
            travelPackageRepository.save(modelMapper.map(travelPackageDTO, TravelPackage.class));
            return true;
        }

    }


    @Override
    public List<TravelPackageDTO> getAllTravelPackages() {
        return travelPackageRepository.findAll().stream().map(travelPackage -> new TravelPackageDTO(
                travelPackage.gettPackageId(),
                travelPackage.getName(),
                travelPackage.getDestination(),
                travelPackage.getImage(),
                travelPackage.getDuration(),
                travelPackage.getPrice(),
                travelPackage.getDescription()
        )).collect(Collectors.toList());
    }

    @Override
    public TravelPackageDTO getTravelPackageById(Long id) {
        TravelPackage travelPackage = travelPackageRepository.findById(id).orElseThrow(() -> new RuntimeException("Package not found"));
        return new TravelPackageDTO(
                travelPackage.gettPackageId(),
                travelPackage.getName(),
                travelPackage.getDestination(),
                travelPackage.getImage(),
                travelPackage.getDuration(),
                travelPackage.getPrice(),
                travelPackage.getDescription()
        );
    }

    @Override
    public TravelPackageDTO updateTravelPackage(Long id, TravelPackageDTO travelPackageDTO) {
        TravelPackage travelPackage = travelPackageRepository.findById(id).orElseThrow(() -> new RuntimeException("Package not found"));
        travelPackage.setName(travelPackageDTO.getName());
        travelPackage.setDestination(travelPackageDTO.getDestination());
        travelPackage.setImage(travelPackageDTO.getImage());
        travelPackage.setDuration(travelPackageDTO.getDuration());
        travelPackage.setPrice(travelPackageDTO.getPrice());
        travelPackage.setDescription(travelPackageDTO.getDescription());
        travelPackageRepository.save(travelPackage);
        return new TravelPackageDTO(
                travelPackage.gettPackageId(),
                travelPackage.getName(),
                travelPackage.getDestination(),
                travelPackage.getImage(),
                travelPackage.getDuration(),
                travelPackage.getPrice(),
                travelPackage.getDescription()
        );
    }

    @Override
    public void deleteTravelPackage(Long id) {
        travelPackageRepository.deleteById(id);
    }
}
