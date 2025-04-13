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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelPackageRepository travelPackageRepository;
    @Autowired
    private EmailServiceImpl emailService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        // Validate input
        if (bookingDTO.getUserId() == null || bookingDTO.gettPackageId() == null) {
            throw new IllegalArgumentException("User ID and Package ID are required");
        }

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookingDTO.getUserId()));

        TravelPackage travelPackage = travelPackageRepository.findById(bookingDTO.gettPackageId())
                .orElseThrow(() -> new RuntimeException("Travel Package not found with ID: " + bookingDTO.gettPackageId()));

        // Format booking date
        String formattedDate = LocalDate.now().format(dateFormatter);

        Booking booking = new Booking();
        booking.setBookingDate(formattedDate);
        booking.setStatus(bookingDTO.getStatus() != null ? bookingDTO.getStatus() : "PENDING, CONFIRM");
        booking.setNumberOfTravelers(bookingDTO.getNumberOfTravelers());
        booking.setUserEmail(user.getEmail());
        booking.setSpecialRequests(bookingDTO.getSpecialRequests());
        booking.setUser(user);
        booking.setTravelPackage(travelPackage);

         bookingRepository.save(booking);

        return convertToDTO(booking);
    }
    @Override
    public void sendBookingConfirmationEmail(User user, TravelPackage travelPackage, Booking booking) {
        try {
            String subject = "Your Booking Confirmation #" + booking.getBookingId();

            String bookingDetails = "Booking ID: " + booking.getBookingId() + "\n" +
                    "Package: " + travelPackage.getName() + "\n" +
                    "Booking Date: " + booking.getBookingDate() + "\n" +
                    "Number of Travelers: " + booking.getNumberOfTravelers() + "\n" +
                    "Status: " + booking.getStatus() + "\n" +
                    "Special Requests: " + (booking.getSpecialRequests() != null ? booking.getSpecialRequests() : "None");

            emailService.sendBookingConfirmationHtml(
                    user.getEmail(),
                    subject,
                    bookingDetails
            );
        } catch (Exception e) {
            // Log the error but don't fail the booking process
            log.error("Failed to send booking confirmation email to " + user.getEmail(), e);
        }
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        if (bookingDTO.getStatus() != null) {
            booking.setStatus(bookingDTO.getStatus());
        }
        if (bookingDTO.getNumberOfTravelers() > 0) {
            booking.setNumberOfTravelers(bookingDTO.getNumberOfTravelers());
        }
        if (bookingDTO.getSpecialRequests() != null) {
            booking.setSpecialRequests(bookingDTO.getSpecialRequests());
        }

        booking = bookingRepository.save(booking);
        return convertToDTO(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with ID: " + id);
        }
        bookingRepository.deleteById(id);
    }

    private BookingDTO convertToDTO(Booking booking) {
        return new BookingDTO(
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getStatus(),
                booking.getUser().getUserId(),
                booking.getTravelPackage().gettPackageId(),
                booking.getUserEmail(),
                booking.getNumberOfTravelers(),
                booking.getSpecialRequests()
        );
    }

}

