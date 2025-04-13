package lk.ijse.travel_booking_system.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.travel_booking_system.dto.PayHereWebhookRequest;
import lk.ijse.travel_booking_system.dto.PaymentDTO;
import lk.ijse.travel_booking_system.entity.Booking;
import lk.ijse.travel_booking_system.entity.Payment;
import lk.ijse.travel_booking_system.repo.BookingRepository;
import lk.ijse.travel_booking_system.repo.PaymentRepository;
import lk.ijse.travel_booking_system.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public PaymentDTO savePayment(PaymentDTO paymentDTO) {
        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = new Payment();

                payment.setAmount(paymentDTO.getAmount());
                payment.setPaymentMethod(paymentDTO.getPaymentMethod());
                payment.setStatus(paymentDTO.getStatus());
                payment.setBooking(booking);

                Payment pid  = payment;
                booking.setPayment(pid);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentDTO;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        PaymentDTO paymentDTO = new PaymentDTO();
        return  paymentDTO;
    }

    @Override
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        payment.setBooking(booking);

        Payment updatedPayment = paymentRepository.save(payment);
        return paymentDTO;
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }


//    private PaymentDTO mapToDTO(Payment payment) {
//        return new PaymentDTO(
//                payment.getPaymentId(),
//                payment.getAmount(),
//                payment.getPaymentMethod(),
//                payment.getStatus(),
//                payment.getBooking().getBookingId()
//        );
//    }
}
