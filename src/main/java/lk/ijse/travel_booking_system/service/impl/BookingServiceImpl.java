package lk.ijse.travel_booking_system.service.impl;

import lk.ijse.travel_booking_system.dto.BookingDTO;
import lk.ijse.travel_booking_system.dto.TravelPackageDTO;
import lk.ijse.travel_booking_system.entity.Booking;
import lk.ijse.travel_booking_system.entity.TravelPackage;
import lk.ijse.travel_booking_system.entity.User;
import lk.ijse.travel_booking_system.repo.BookingRepository;
import lk.ijse.travel_booking_system.repo.TravelPackageRepository;
import lk.ijse.travel_booking_system.repo.UserRepository;
import lk.ijse.travel_booking_system.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelPackageRepository travelPackageRepository;
@Override
   public List<TravelPackageDTO> getAllPackages() {
        return travelPackageRepository.findAll().stream()
                .map(tpkg -> new TravelPackageDTO(
                        tpkg.gettPackageId(),
                        tpkg.getName(),
                        tpkg.getDestination(),
                        tpkg.getImage(),
                        tpkg.getDuration(),
                        tpkg.getPrice(),
                        tpkg.getDescription(),
                        tpkg.getGuide()
                        )).collect(Collectors.toList());
    }

    @Override
    public BookingDTO saveBooking(BookingDTO bookingDTO) {
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TravelPackage travelPackage = travelPackageRepository.findById(bookingDTO.gettPackageId())
                .orElseThrow(() -> new RuntimeException("Travel Package not found"));

        Booking booking = new Booking(
                bookingDTO.getBookingId(),
                bookingDTO.getBookingDate(),
                bookingDTO.getStatus(),
                user,
                travelPackage,
                null
        );
        booking = bookingRepository.save(booking);

        return new BookingDTO(
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getStatus(),
                booking.getUser().getUserId(),
                booking.getTravelPackage().gettPackageId()
        );
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(booking -> new BookingDTO(
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getStatus(),
                booking.getUser().getUserId(),
                booking.getTravelPackage().gettPackageId()
        )).collect(Collectors.toList());
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        return new BookingDTO(
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getStatus(),
                booking.getUser().getUserId(),
                booking.getTravelPackage().gettPackageId()
        );
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TravelPackage travelPackage = travelPackageRepository.findById(bookingDTO.gettPackageId())
                .orElseThrow(() -> new RuntimeException("Travel Package not found"));

        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setStatus(bookingDTO.getStatus());
        booking.setUser(user);
        booking.setTravelPackage(travelPackage);

        bookingRepository.save(booking);

        return new BookingDTO(
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getStatus(),
                booking.getUser().getUserId(),
                booking.getTravelPackage().gettPackageId()
        );
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
