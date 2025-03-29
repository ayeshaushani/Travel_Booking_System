package lk.ijse.travel_booking_system.controller;

import lk.ijse.travel_booking_system.dto.BookingDTO;
import lk.ijse.travel_booking_system.dto.TravelPackageDTO;
import lk.ijse.travel_booking_system.service.BookingService;
import lk.ijse.travel_booking_system.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private TravelPackageService travelPackageService;

    @PostMapping
    public ResponseEntity<BookingDTO> saveBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.saveBooking(bookingDTO));
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking Deleted Successfully");
    }
    @GetMapping("/packages")
    public ResponseEntity<List<TravelPackageDTO>> getTravelPackages() {
        return ResponseEntity.ok(travelPackageService.getAllTravelPackages());
    }
}
