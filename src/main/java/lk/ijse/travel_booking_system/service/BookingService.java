package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.BookingDTO;
import lk.ijse.travel_booking_system.dto.TravelPackageDTO;

import java.util.List;

public interface BookingService {
    List<TravelPackageDTO> getAllPackages();

    BookingDTO saveBooking(BookingDTO bookingDTO);
    List<BookingDTO> getAllBookings();
    BookingDTO getBookingById(Long id);
    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);
    void deleteBooking(Long id);
}
