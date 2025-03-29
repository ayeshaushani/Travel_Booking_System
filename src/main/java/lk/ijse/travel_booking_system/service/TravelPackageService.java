package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.TravelPackageDTO;
import java.util.List;

public interface TravelPackageService {
    boolean saveTravelPackage(TravelPackageDTO travelPackageDTO);
    List<TravelPackageDTO> getAllTravelPackages();
    TravelPackageDTO getTravelPackageById(Long id);
    TravelPackageDTO updateTravelPackage(Long id, TravelPackageDTO travelPackageDTO);
    void deleteTravelPackage(Long id);
}