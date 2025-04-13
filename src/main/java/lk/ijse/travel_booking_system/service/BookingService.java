package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.BookingDTO;
import lk.ijse.travel_booking_system.dto.TravelPackageDTO;
import lk.ijse.travel_booking_system.entity.Booking;
import lk.ijse.travel_booking_system.entity.TravelPackage;
import lk.ijse.travel_booking_system.entity.User;

import java.util.List;

public interface BookingService {
    List<TravelPackageDTO> getAllPackages();

    BookingDTO saveBooking(BookingDTO bookingDTO);

    void sendBookingConfirmationEmail(User user, TravelPackage travelPackage, Booking booking);

    List<BookingDTO> getAllBookings();
    BookingDTO getBookingById(Long id);
    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);
    void deleteBooking(Long id);
}
